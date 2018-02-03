package phlaxyr.arrows.codepatcher;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.tree.AbstractInsnNode.*;
import static phlaxyr.arrows.codepatcher.PatchTransformer.get;
import static phlaxyr.arrows.codepatcher.PatchTransformer.obfdesc;

import java.util.Iterator;

// import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.util.Tuple;
import phlaxyr.arrows.Arrows;

class Container_slotChangedCraftingGrid implements MethodFixer {

	@Override
	public boolean isTargetMethod(MethodNode m, boolean obf) {
		return m.name.equals(obf ? "a" : "slotChangedCraftingGrid") 
				&& m.desc.equals(PatchTransformer.obfdesc(
				"(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V", false));
	}

	@Override
	public Tuple<AbstractInsnNode, Integer> whereToInject(Iterator<AbstractInsnNode> iterator, boolean obf) {
		// before: /*
//		MethodVisitor mv = null;
//		mv.visitVarInsn(ALOAD, 4);
//		mv.visitInsn(ICONST_0);
//		mv.visitVarInsn(ALOAD, 6);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/inventory/InventoryCraftResult", "setInventorySlotContents", "(ILnet/minecraft/item/ItemStack;)V", false);

		AbstractInsnNode theone;
		int varNumOfItemStack;
		VarInsnNode varinsn;
		while(iterator.hasNext()) {
			AbstractInsnNode insn = iterator.next();
			if(insn.getType() == VAR_INSN) {
				varinsn = (VarInsnNode) insn;
				if (varinsn.getOpcode() == ALOAD && varinsn.var == 4) {

					theone = varinsn;
					// we found the one to inject before
					// but this is validation
					// succes: next insn
					insn = iterator.next();
					if (insn.getType() == INSN && insn.getOpcode() == ICONST_0) {

						// success: next insn
						insn = iterator.next();
						if (insn.getType() == VAR_INSN && insn.getOpcode() == ALOAD) {

							varinsn = (VarInsnNode) insn;
							// this may have changed. if it did, we record it
							// if it otherwise doesn't match then it will be dropped

							varNumOfItemStack = varinsn.var;
							// succes: next
							insn = iterator.next();
							if (insn.getType() == METHOD_INSN && insn.getOpcode() == INVOKEVIRTUAL) {
								MethodInsnNode methodinsn = (MethodInsnNode) insn;
								//"net/minecraft/inventory/InventoryCraftResult", "setInventorySlotContents", "(ILnet/minecraft/item/ItemStack;)V", false
								// owner, name, desc, itf
								if(			methodinsn.owner	.equals(	get("net/minecraft/inventory/InventoryCraftResult", obf)	)
										&& 	methodinsn.name 	.equals( 	obf ? "a" : "setInventorySlotContents" 						)
										&& 	methodinsn.desc 	.equals( 	obfdesc("(ILnet/minecraft/item/ItemStack;)V", obf) 			)
										&& 	methodinsn.itf == false) {
									// finally validated
									return new Tuple<AbstractInsnNode, Integer>(theone, varNumOfItemStack);
								}
							}

						}

					}
				}
				
			}
		}
		return null;
	}

	@Override
	public InsnList getInsnsToInject(boolean obf, int varCode) {
		// code:
//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitVarInsn(ALOAD, 3);
//		mv.visitVarInsn(ALOAD, 6);
//		mv.visitVarInsn(ALOAD, 1);
//		mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/arrows/craftingarrow/ArrowManager", "injectArrowResult", "(Lnet/minecraft/inventory/Container;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;)Lnet/minecraft/item/ItemStack;", false);
//		mv.visitVarInsn(ASTORE, 6);
		InsnList insns = new InsnList();
		insns.add(new FrameNode(F_SAME, 0, null, 0, null));
		insns.add(new VarInsnNode(ALOAD, 0)); // this
		insns.add(new VarInsnNode(ALOAD, 3)); // InventoryCrafting
		insns.add(new VarInsnNode(ALOAD, varCode)); // default is 6: varcode of ItemStack
		insns.add(new VarInsnNode(ALOAD, 1)); // world
		insns.add(new MethodInsnNode(INVOKESTATIC, 
				"phlaxyr/arrows/craftingarrow/ArrowManager", 
				"injectArrowResult", 
				obfdesc("(Lnet/minecraft/inventory/Container;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;)Lnet/minecraft/item/ItemStack;", obf), 
				false));
		
		insns.add(new VarInsnNode(ASTORE, varCode));
		return insns;
	}

	@Override
	public void onCodeNotFound() {
		Arrows.lumberjack.error("NOT FOUND! Arrows can't seem to find the code line \"res.setInventorySlotContents(0, itemstack);\"!");
		throw new IllegalStateException("Arrows can't seem to find the code line \"res.setInventorySlotContents(0, itemstack);\"!");
		
	}
	public void inject(MethodNode method, boolean obf) {
		Arrows.lumberjack.info("Attempting to patch "+method.name);
		Iterator<AbstractInsnNode>insns = method.instructions.iterator();
		Tuple<AbstractInsnNode, Integer> tuple = whereToInject(insns, obf);
		AbstractInsnNode injectHere = tuple.getFirst();
		int varCode = tuple.getSecond();
		if(injectHere == null) {
			onCodeNotFound();
		}
		// here's the change: insertBefore
		method.instructions.insertBefore(injectHere, getInsnsToInject(obf, varCode));
		Arrows.lumberjack.info("Patching "+method.name+" Complete!");
	}
	
}