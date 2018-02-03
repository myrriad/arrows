package phlaxyr.arrows.codepatcher;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.util.Tuple;
import phlaxyr.arrows.Arrows;

interface MethodFixer {
	
	public boolean isTargetMethod(MethodNode method, boolean obf);
	public Tuple<AbstractInsnNode, Integer> whereToInject(Iterator<AbstractInsnNode> iterator, boolean obf);
	public InsnList getInsnsToInject(boolean obf, int varCode);
	/**
	 * Does not check if it's the right method
	 * Does everything else
	 * @param method
	 * @param obf
	 */
	public default void inject(MethodNode method, boolean obf) {
		Arrows.lumberjack.info("Attempting to patch "+method.name);
		Iterator<AbstractInsnNode>insns = method.instructions.iterator();
		Tuple<AbstractInsnNode, Integer> tuple = whereToInject(insns, obf);
		AbstractInsnNode injectHere = tuple.getFirst();
		int varCode = tuple.getSecond();
		if(injectHere == null) {
			onCodeNotFound();
		}
		method.instructions.insert(injectHere, getInsnsToInject(obf, varCode));
		Arrows.lumberjack.info("Patching "+method.name+" Complete!");
	}
	public void onCodeNotFound();
	
}