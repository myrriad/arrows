package phlaxyr.forcearrows.codepatcher;

import java.util.Iterator;

import static phlaxyr.forcearrows.codepatcher.ObfUtil.obf;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.collect.Lists;


class ClassFixer {
	List<MethodFixer> fixers;
	String target;
	public ClassFixer(String targetClassName, List<MethodFixer>fixers) {
		this.target = targetClassName;
		this.fixers = fixers;
	}
	public ClassFixer(String targetClassName, MethodFixer...fixers) {
		this(targetClassName,Lists.newArrayList(fixers));
	}
	
	
	
	public byte[] fixClass(byte[] bytes, String classNameOfBytes) {
		
		boolean obfuscated;
		if(classNameOfBytes.equals(target)) {
			obfuscated = false;
		} else if(classNameOfBytes.equals(obf(target))) {
			obfuscated = true;
		} else return bytes;
		
		System.out.println("********* INSIDE " + (obfuscated ? "OBFUSCATED" : "NORMAL") + " TRANSFORMER ABOUT TO PATCH: "+target);

		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode m = methods.next();
			for(MethodFixer fixer : fixers) {
				if(fixer.isTargetMethod(m, obfuscated)) {
					fixer.inject(m, obfuscated);
				
				}
			}
			
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
}