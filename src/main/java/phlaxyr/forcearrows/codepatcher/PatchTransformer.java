package phlaxyr.forcearrows.codepatcher;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.tree.AbstractInsnNode.*;

import java.util.Iterator;
import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

// import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
// import phlaxyr.forcearrows.event.ArrowManager;
@TransformerExclusions({"me.guichaguri.tickratechanger"})
public class PatchTransformer implements IClassTransformer {
	public static boolean obfuscated;
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		// String target = "net.minecraft.client.gui.inventory.GuiCrafting";
		String container = "net.minecraft.inventory.Container";
		String containerworkbench = "net.minecraft.inventory.ContainerWorkbench";
		if(name.equals(container)) {
			obfuscated = false;
			return patchContainer(name, basicClass, false);
		}
		if(name.equals(obfuscate(container))) {
			obfuscated = true;
			return patchContainer(name, basicClass, true);
		}
		
		if(name.equals(containerworkbench)) {
			obfuscated = false;
			return patchContainerWorkbench(basicClass, false);
		}
		if(name.equals(obfuscate(containerworkbench))) {
			obfuscated = true;
			return patchContainerWorkbench(basicClass, true);
		}
		

		

		return basicClass;
	}
	// Copied from http://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing
	public byte[] patchContainer(String name, byte[] bytes, boolean obfuscated) {
		System.out.println("********* INSIDE " + (obfuscated ? "OBFUSCATED" : "NORMAL") + " TRANSFORMER ABOUT TO PATCH: Container");
		ClassNameUtil cn = new ClassNameUtil(obfuscated);
		String targetMethodName = obfuscated ? "a" : "slotChangedCraftingGrid";
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode m = methods.next();

			// Check for the right method and return/parameter values
			// if it isn't, ignore
			// (Lnet/minecraft/inventory/IInventory;)V
			//(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V
			if (!(m.name.equals(targetMethodName) 
					&& m.desc.equals(obfdesc(
					"(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V"
							, cn)))) continue;
			int currentAloadID = -1;
			VarInsnNode insertionNode = null;
			System.out.println("********* Inside target method!");
			ListIterator<AbstractInsnNode> insns = m.instructions.iterator();
			while(insns.hasNext()) {
				AbstractInsnNode insn = insns.next();			
				if (insn.getType() == METHOD_INSN) {
					MethodInsnNode methodinsn = (MethodInsnNode) insn;
					// m.visitMethodInsn(INVOKESTATIC, "net/minecraft/item/crafting/CraftingManager", "findMatchingRecipe", "(Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/world/World;)Lnet/minecraft/item/crafting/IRecipe;", false);
					
					if (insn.getOpcode() == INVOKESTATIC) // 7
							if(methodinsn.owner.equals(cn.get("net/minecraft/item/crafting/CraftingManager"))) // 6
							if(methodinsn.name.equals(obfuscated ? "b" : "findMatchingRecipe")) // 5
							if(methodinsn.desc.equals(obfdesc("(Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/world/World;)Lnet/minecraft/item/crafting/IRecipe;",cn)))
							if(methodinsn.itf == false) { // 3
						// get the next node to see the aload value for
						// irecipe
						// mv.visitVarInsn(ASTORE, 7);
						insn = insns.next();
						if (insn.getType() == VAR_INSN) {
							VarInsnNode varinsn = (VarInsnNode) insn;
							if (varinsn.getOpcode() == ASTORE) {
								currentAloadID = varinsn.var;
								insertionNode = varinsn;
								// success! we found it!
								break;
								// m.visitVarInsn(ALOAD, 1);
							} else System.out.println(varinsn.getOpcode());
						} else {
							System.out.println(insn.getType());
						}
					} else System.out.println(methodinsn.itf);
					else System.out.println(methodinsn.desc);
					else System.out.println(methodinsn.name);
					else System.out.println(methodinsn.owner);
					else System.out.println(7);
				} 
			}
			if(insertionNode == null || currentAloadID == -1) {
				System.out.println("NOT FOUND!");
				throw new IllegalStateException("PatchPlugin can't seem to find the code line \"IRecipe irecipe = CraftingManager.findMatchingRecipe(inv, worldIn);\"!");
			}
			System.out.println("ALOADING: "+currentAloadID);

			InsnList toInject = new InsnList();
			/**this*/
			toInject.add(new VarInsnNode(ALOAD, 0));
			
			
			/**parameters*/
			toInject.add(new VarInsnNode(ALOAD, 1));
			toInject.add(new VarInsnNode(ALOAD, 3));
			
			// toInject.add(new VarInsnNode(ALOAD, 0));
			// toInject.add(new VarInsnNode(ALOAD, 0));
			//    GETFIELD phlaxyr/forcearrows/codepatcher/ProposedChangedContainerWorkbench.world : Lnet/minecraft/world/World;

			/**SHOULD BE 7: This is the irecipe*/
			toInject.add(new VarInsnNode(ALOAD, currentAloadID));
			
			// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/event/ArrowManager", "recipe", "(Lnet/minecraft/item/crafting/IRecipe;)Lnet/minecraft/item/crafting/IRecipe;", false);
			// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/event/ArrowManager", "recipe", "(Lnet/minecraft/inventory/Container;Lnet/minecraft/world/World;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/item/crafting/IRecipe;)Lnet/minecraft/item/crafting/IRecipe;", false);

			toInject.add(new MethodInsnNode(
					INVOKESTATIC, 
					"phlaxyr/forcearrows/event/ArrowManager", 
					"recipe", 
					obfdesc(
						"(Lnet/minecraft/inventory/Container;"
						+ "Lnet/minecraft/world/World;"
						+ "Lnet/minecraft/inventory/InventoryCrafting;"
						+ "Lnet/minecraft/item/crafting/IRecipe;)"
						+ "Lnet/minecraft/item/crafting/IRecipe;",cn)
					, false));
			
			toInject.add(new VarInsnNode(ASTORE, currentAloadID));

			m.instructions.insert(insertionNode, toInject);

			System.out.println("Patching Complete!");
			break;
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
	public byte[] patchContainerWorkbench(byte[] bytes, boolean obfuscated) {
		System.out.println("********* INSIDE " + (obfuscated ? "OBFUSCATED" : "NORMAL") + " TRANSFORMER ABOUT TO PATCH: ContainerWorkbench");
		ClassNameUtil cn = new ClassNameUtil(obfuscated);
		String targetMethodName = obfuscated ? "b" : "transferStackInSlot";
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode m = methods.next();

			// Check for the right method and return/parameter values
			// if it isn't, ignore
			// (Lnet/minecraft/inventory/IInventory;)V
			//(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V
			if (!(m.name.equals(targetMethodName) 
					&& m.desc.equals(obfdesc(
							//(Lnet/minecraft/entity/player/EntityPlayer;I)Lnet/minecraft/item/ItemStack;
					"(Lnet/minecraft/entity/player/EntityPlayer;I)Lnet/minecraft/item/ItemStack;"
							, cn)))) continue;
			int currentAloadID = -1;
			VarInsnNode insertionNode = null;
			System.out.println("********* Inside target method!");
			
			ListIterator<AbstractInsnNode> insns = m.instructions.iterator();
			while(insns.hasNext()) {
				AbstractInsnNode insn = insns.next();	
				
				// test for
				// mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;", true);


				if (insn.getType() == METHOD_INSN) {
					MethodInsnNode methodinsn = (MethodInsnNode) insn;
					if (insn.getOpcode() == INVOKEINTERFACE) // 7
							if(methodinsn.owner.equals("java/util/List")) // 6
							if(methodinsn.name.equals("get")) // 5
							if(methodinsn.desc.equals("(I)Ljava/lang/Object;"))
							if(methodinsn.itf == true) { // 3
						// get the next node to see the aload value for
						// irecipe
						insn = insns.next();
						if (insn.getType() == TYPE_INSN) {
							// m.visitTypeInsn(CHECKCAST, "");
							TypeInsnNode typeinsn = (TypeInsnNode) insn;
							if (typeinsn.getOpcode() == CHECKCAST) 
							if (typeinsn.desc.equals(cn.get("net/minecraft/inventory/Slot"))){
								// mv.visitVarInsn(ASTORE, 4);
								insn = insns.next();
								if(insn.getType() == VAR_INSN) {
									VarInsnNode varinsn = (VarInsnNode) insn;
									if(varinsn.getOpcode() == ASTORE) {
										// success we found it
										currentAloadID = varinsn.var;
										insertionNode = varinsn;
									}
								}
								
							} else System.out.println(typeinsn.desc);
							else System.out.println(typeinsn.getOpcode());
						} else {
							System.out.println(insn.getType());
						}
					} else System.out.println(methodinsn.itf);
					else System.out.println(methodinsn.desc);
					else System.out.println(methodinsn.name);
					else System.out.println(methodinsn.owner);
					// else System.out.println(7);
				} 
			}
			if(insertionNode == null || currentAloadID == -1) {
				System.out.println("NOT FOUND! PatchPlugin can't seem to find the code line \"Slot slot = this.inventorySlots.get(index);\"!");
				throw new IllegalStateException("PatchPlugin can't seem to find the code line \"Slot slot = this.inventorySlots.get(index);\"!");
			}
			System.out.println("ALOADING: (should be 4) "+currentAloadID);

			InsnList toInject = new InsnList();
			// ALOAD 0
			//m.visitVarInsn(ALOAD, 4);
			// m.visitVarInsn(ALOAD, 0);
			// m.visitFieldInsn(GETFIELD, "phlaxyr/forcearrows/codepatcher/ProposedChangedContainerWorkbench", "pos", "Lnet/minecraft/util/math/BlockPos;");
			// m.visitVarInsn(ALOAD, 0);
			// m.visitFieldInsn(GETFIELD, "phlaxyr/forcearrows/codepatcher/ProposedChangedContainerWorkbench", "world", "Lnet/minecraft/world/World;");
			// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/event/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/inventory/Slot;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)Z", false);

			
			//m.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/event/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/Slot;)Z", false);
			// //m.visitJumpInsn(IFEQ, l3);
			// m.visitFieldInsn(GETSTATIC, "net/minecraft/item/ItemStack", "EMPTY", "Lnet/minecraft/item/ItemStack;");
			
			m.visitInsn(ARETURN);

			/**THIS*/
			toInject.add(new VarInsnNode(ALOAD, 0));
			
			/**SHOULD BE 4: This is the slot*/
			toInject.add(new VarInsnNode(ALOAD, currentAloadID));
			
			/**POS*/
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, cn.get("net/minecraft/inventory/ContainerWorkbench"), obfuscated ? "h" : "pos", obfdesc("Lnet/minecraft/util/math/BlockPos;",cn)));
			
			/**WORLD*/
			toInject.add(new VarInsnNode(ALOAD, 0));
			toInject.add(new FieldInsnNode(GETFIELD, cn.get("net/minecraft/inventory/ContainerWorkbench"), obfuscated ? "g" : "world", obfdesc("Lnet/minecraft/world/World;",cn)));
			
			
			
			
			toInject.add(new MethodInsnNode(
					INVOKESTATIC, 
					"phlaxyr/forcearrows/event/ArrowManager", 
					"onCraftArrow", 
					obfdesc(
							"(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/inventory/Slot;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)Z",cn)// false);"(Lnet/minecraft/inventory/Slot;)Z",cn)
					, false));
			
			// mv.visitJumpInsn(IFEQ, l3);
			// mv.visitFieldInsn(GETSTATIC, "net/minecraft/item/ItemStack", "EMPTY", "Lnet/minecraft/item/ItemStack;");
			// mv.visitInsn(ARETURN);
			// mv.visitLabel(l3);
			// mv.visitLineNumber(72, l3);
			
			LabelNode iffalse = new LabelNode();
			toInject.add(new JumpInsnNode(IFEQ, iffalse));
			toInject.add(new FieldInsnNode(GETSTATIC, "net/minecraft/item/ItemStack", (obfuscated ? "a" : "EMPTY"), "Lnet/minecraft/item/ItemStack;"));
			toInject.add(new InsnNode(ARETURN));
			toInject.add(iffalse);
			
			m.instructions.insert(insertionNode, toInject);

			System.out.println("Patching Complete!");
			break;
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
	
	static class ClassNameUtil {
		private boolean obfuscate;
		public ClassNameUtil(boolean obfuscate) {
			this.obfuscate = obfuscate;
		}
		
		public String get(String str) {
			if(!obfuscate) return str;
			return obfuscate(str);
		}
	}
	public static String obfuscate(String str) {
		String stri = str.replace('/', '.');
		switch(stri) {
		case "net.minecraft.inventory.ContainerWorkbench":
			return "afx";
				
		case "net.minecraft.block.BlockWorkbench":
			return "app";
		case "net.minecraft.block.BlockWorkbench.InterfaceCraftingTable":
			return "app$a";
		case "net.minecraft.client.gui.inventory.GuiCrafting":
			return "bml";
		case "net.minecraft.inventory.Container":
			return "afp";
		case "net.minecraft.world.World":
			return "ams";
		case "net.minecraft.entity.player.EntityPlayer":
			return "aeb";
		case "net.minecraft.inventory.InventoryCrafting":
			return "afw";
		case "net.minecraft.inventory.InventoryCraftResult":
			return "agl";
		case "net.minecraft.item.crafting.IRecipe":
			return "akr";
		case "net.minecraft.item.ItemStack":
			return "ain";
		case "net.minecraft.inventory.Slot":
			return "agp";
		case "net.minecraft.util.math.BlockPos":
			return "et";
		case "net.minecraft.item.crafting.CraftingManager":
			return "aks";
		}
		return str;
	}
	public static String obfdesc(String desc, ClassNameUtil obfuscator) {
		if(!(desc.charAt(0) == '(')) return desc;
		int i = 1;
		String retn = "(";
		while(i < desc.length()) {
			char c = desc.charAt(i);
			if(c == 'L') {
				retn = retn + 'L';
				String methodname = "";
				i++; // i is the first index of methodname
				c = desc.charAt(i); // c is the first character of methodname
				while(c != ';' && i < desc.length() - 1) { // check before appending
					methodname = methodname + c; // append c to methodname
					i++;
					c = desc.charAt(i);
					// System.out.println(i);
				}
				// c = ';'
				// i at the ';'
				retn = retn + obfuscator.get(methodname) + ";";
				i++;
				continue;
			}
			
			retn = retn + c;
			i++;

			continue;

		}
		return retn;
		// "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V"
		//  (Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)
	}
	
	public static
	void main(String[] args) {
		System.out.println(new ClassNameUtil(true).get("net/minecraft/world/World"));
		System.out.println(obfdesc("(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V", new ClassNameUtil(true)));
	}
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/event/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/Slot;)Z", false);

}
