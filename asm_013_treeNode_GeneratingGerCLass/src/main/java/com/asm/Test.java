package com.asm;

import java.io.PrintWriter;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * 
 *public class Generic3<T extends Number> {//<T:Ljava/lang/Number;>Ljava/lang/Object;
	
	public T test() {
		
		
		return null;
	}
	
	public <E extends T> Class<E> clazz(List<E> e) {
		
		return null;
	}
	
	static <T> Class<? extends T> m (int n) {
		
		return null;
	}
	
	public <E extends T> Class<? extends E> mm (List<T> list) {
		
		return null;
	}

}
 * 
 * @author may
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
			
		Test test = new Test();
		
		ClassNode cn = test.gen();
		
		ClassWriter cw = new ClassWriter(0);
		
		PrintWriter pw = new PrintWriter(System.out);
		
		TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);
		
		cn.accept(tcv);
		
		
		
	}
	
	
	public ClassNode gen() {
		
		ClassNode cn = new ClassNode(Opcodes.ASM5);
		
		cn.access = Opcodes.ACC_PUBLIC;
		
		cn.name = "Generic3";
		
		cn.signature = "<T:Ljava/lang/Number;>Ljava/lang/Object;";
		
		cn.superName = "java/lang/Object";
		
		MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		
		InsnList il_1 = mn.instructions;
		
		il_1.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		il_1.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false));
		
		il_1.add(new InsnNode(Opcodes.RETURN));
		
		mn.maxLocals = 1;
		
		mn.maxStack = 1;
		
		cn.methods.add(mn);
		
		
		MethodNode clazz = new MethodNode(Opcodes.ACC_PUBLIC, "clazz", "(Ljava/util/List;)Ljava/lang/Class;", "<E:TT;>(Ljava/util/List<TE;>;)Ljava/lang/Class<TE;>;", null);
		
		InsnList il_2 = clazz.instructions;
		
		
		il_2.add(new InsnNode(Opcodes.RETURN));
		
		clazz.maxLocals = 2;
		
		clazz.maxStack = 1;
		
		cn.methods.add(clazz);
		
		
		
		
		return cn;
		
	}
	
	
}
