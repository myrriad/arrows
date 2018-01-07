package phlaxyr.forcearrows.codepatcher;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.tree.AbstractInsnNode.*;

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

import net.minecraft.util.Tuple;
import phlaxyr.forcearrows.ForceArrows;

class ContainerWorkbench_transferStackInSlot implements MethodFixer {
	@Override
	public boolean isTargetMethod(MethodNode m, boolean obf)
	{
		return m.name.equals(obf ? "b" : "transferStackInSlot") 
				&& m.desc.equals(PatchTransformer.obfdesc(
				"(Lnet/minecraft/entity/player/EntityPlayer;I)Lnet/minecraft/item/ItemStack;", false));

	}
	// GuiCrafting g;

	@Override
	public Tuple<AbstractInsnNode, Integer> whereToInject(Iterator<AbstractInsnNode>insns, boolean obf)
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
								return new Tuple<AbstractInsnNode, Integer>(insn, -1);
								
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
		// dont really need the varCode
		InsnList toInject = new InsnList();
		// m.visitInsn(ARETURN);

		/**THIS*/
		toInject.add(new VarInsnNode(ALOAD, 0));
		
		/**POS*/
		toInject.add(new VarInsnNode(ALOAD, 0));
		toInject.add(new FieldInsnNode(GETFIELD, PatchTransformer.get("net/minecraft/inventory/ContainerWorkbench", obf), obf ? "h" : "pos", PatchTransformer.obfdesc("Lnet/minecraft/util/math/BlockPos;",false)));
		
		/**WORLD*/
		toInject.add(new VarInsnNode(ALOAD, 0));
		toInject.add(new FieldInsnNode(GETFIELD, PatchTransformer.get("net/minecraft/inventory/ContainerWorkbench", obf), obf ? "g" : "world", PatchTransformer.obfdesc("Lnet/minecraft/world/World;",false)));
		
		/**ENTITYPLAYER*/
		toInject.add(new VarInsnNode(ALOAD, 1));
		//mv.visitVarInsn(ALOAD, 1);
		// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/craftingarrow/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Z", false);

		
		
		// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/forcearrows/craftingarrow/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)Z", false);

		toInject.add(new MethodInsnNode(
				INVOKESTATIC, 
				"phlaxyr/forcearrows/craftingarrow/ArrowManager", 
				"onCraftArrow", 
				PatchTransformer.obfdesc(
						"(Lnet/minecraft/inventory/ContainerWorkbench;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Z",false)// false);"(Lnet/minecraft/inventory/Slot;)Z",cn)
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
					PatchTransformer.get("net/minecraft/item/ItemStack",obf), 
					PatchTransformer.get("net/minecraft/inventory/Slot",obf), 
					PatchTransformer.get("net/minecraft/item/ItemStack",obf)
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


}