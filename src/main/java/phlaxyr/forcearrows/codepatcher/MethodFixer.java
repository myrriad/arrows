package phlaxyr.forcearrows.codepatcher;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import phlaxyr.forcearrows.ForceArrows;

interface MethodFixer {
	
	public boolean isTargetMethod(MethodNode method, boolean obf);
	public AbstractInsnNode whereToInject(Iterator<AbstractInsnNode> iterator, boolean obf);
	public InsnList getInsnsToInject(boolean obf);
	/**
	 * Does not check if it's the right method
	 * Does everything else
	 * @param method
	 * @param obf
	 */
	public default void inject(MethodNode method, boolean obf) {
		ForceArrows.lumberjack.info("Attempting to patch "+method.name);
		Iterator<AbstractInsnNode>insns = method.instructions.iterator();
		AbstractInsnNode injectHere = whereToInject(insns, obf);
		if(injectHere == null) {
			onCodeNotFound();
		}
		method.instructions.insert(injectHere, getInsnsToInject(obf));
		ForceArrows.lumberjack.info("Patching "+method.name+" Complete!");
	}
	public void onCodeNotFound();
	
}