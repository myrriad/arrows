package phlaxyr.forcearrows.codepatcher;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.F_APPEND;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.tree.AbstractInsnNode.JUMP_INSN;
import static org.objectweb.asm.tree.AbstractInsnNode.VAR_INSN;
import static phlaxyr.forcearrows.codepatcher.ObfUtil.obf;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

// import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.launchwrapper.IClassTransformer;
import phlaxyr.forcearrows.ForceArrows;
// import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
// import phlaxyr.forcearrows.craftingarrow.ArrowManager;

public class PatchTransformer implements IClassTransformer {
	// public static boolean obfuscated;
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		// String target = "net.minecraft.client.gui.inventory.GuiCrafting";
		// String container = "net.minecraft.inventory.Container";
//		if(name.equals(container)) {
//			obfuscated = false;
//			return patchContainer(name, basicClass, false);
//		}
//		if(name.equals(obfuscate(container))) {
//			obfuscated = true;
//			return patchContainer(name, basicClass, true);
//		}
		ClassFixer containerworkbench = new ClassFixer("net.minecraft.inventory.ContainerWorkbench", new MethodFixer() {
			@Override
			public boolean isTargetMethod(MethodNode m, boolean obf)
			{
				return m.name.equals(obf ? "b" : "transferStackInSlot") 
						&& m.desc.equals(obfdesc(
						"(Lnet/minecraft/entity/player/EntityPlayer;I)Lnet/minecraft/item/ItemStack;", false));

			}

			@Override
			public AbstractInsnNode whereToInject(Iterator<AbstractInsnNode>insns, boolean obf)
			{
				// int currentAloadID = -1;
				// AbstractInsnNode insertionNode = null;
				
				while(insns.hasNext()) {
					AbstractInsnNode insn = insns.next();	
					
					VarInsnNode varinsn;
					if(insn.getType() == VAR_INSN)
					{
						varinsn = (VarInsnNode) insn;
						if(varinsn.getOpcode() == ILOAD) {
							if(varinsn.var == 2) {
								// success1
								insn = insns.next();
								if(insn.getType() == JUMP_INSN)
								{
									JumpInsnNode jumpinsn = (JumpInsnNode) insn;
									if(jumpinsn.getOpcode() == IFNE) {
										// success2
										// the label after the jump actually shouldn't matter
										
										
										// boom - insert it at that instruction
										return insn;
										
									}
								}
							}
						}
					}
				}
				return null;
			}
			@Override
			public InsnList getInsnsToInject(boolean obf) {
				InsnList toInject = new InsnList();
				// m.visitInsn(ARETURN);
	
				/**THIS*/
				toInject.add(new VarInsnNode(ALOAD, 0));
				
				/**POS*/
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new FieldInsnNode(GETFIELD, get("net/minecraft/inventory/ContainerWorkbench", obf), obf ? "h" : "pos", obfdesc("Lnet/minecraft/util/math/BlockPos;",false)));
				
				/**WORLD*/
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new FieldInsnNode(GETFIELD, get("net/minecraft/inventory/ContainerWorkbench", obf), obf ? "g" : "world", obfdesc("Lnet/minecraft/world/World;",false)));
				
				
				//"(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)Z"
				
				toInject.add(new MethodInsnNode(
						INVOKESTATIC, 
						"phlaxyr/forcearrows/craftingarrow/ArrowManager", 
						"onCraftArrow", 
						obfdesc(
								"(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)Z",false)// false);"(Lnet/minecraft/inventory/Slot;)Z",cn)
						, false));
				
				// mv.visitJumpInsn(IFEQ, l3);
				// mv.visitFieldInsn(GETSTATIC, "net/minecraft/item/ItemStack", "EMPTY", "Lnet/minecraft/item/ItemStack;");
				// mv.visitInsn(ARETURN);
				// mv.visitLabel(l3);
				// mv.visitLineNumber(72, l3);
				
				LabelNode iffalse = new LabelNode();
				toInject.add(new JumpInsnNode(IFEQ, iffalse));
				toInject.add(new FieldInsnNode(GETSTATIC, "net/minecraft/item/ItemStack", (obf ? "a" : "EMPTY"), "Lnet/minecraft/item/ItemStack;"));
				toInject.add(new InsnNode(ARETURN));
				toInject.add(iffalse);
				toInject.add(
					new FrameNode(
						F_APPEND,
						3,
						new Object[] {
							get("net/minecraft/item/ItemStack",obf), 
							get("net/minecraft/inventory/Slot",obf), 
							get("net/minecraft/item/ItemStack",obf)
						},
						0,
						null
					)
				);
				return toInject;
			}

			@Override
			public void onCodeNotFound()
			{
				ForceArrows.lumberjack.error("NOT FOUND! ForceArrows can't seem to find the code line \"if (index == 0){\"!");
				throw new IllegalStateException("ForceArrows can't seem to find the code line \"if (index == 0){\"!");
				
			}
		});
		byte[] bytes = basicClass;
		bytes = containerworkbench.fixClass(bytes, name);
		
		

		

		return basicClass;
	}

	static class ClassNameUtil {
		private boolean obfuscate;
		public ClassNameUtil(boolean obfuscate) {
			this.obfuscate = obfuscate;
		}
		
		public String get(String str) {
			return PatchTransformer.get(str, obfuscate);
		}
	}
	public static String get(String str, boolean obf) {
		if(!obf) return str;
			return obf(str);
	}
	
	public static String obfdesc(String desc, boolean obfuscated) {
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
				retn = retn + get(methodname, obfuscated) + ";";
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
		System.out.println(get("net/minecraft/world/World", true));
		System.out.println(obfdesc("(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V", true));
	}
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/craftingarrow/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/Slot;)Z", false);

}
