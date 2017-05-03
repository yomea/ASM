package com.asm;

import java.io.PrintWriter;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * 
 * @author may
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
			
		ClassWriter cw = new ClassWriter(0);
		
		PrintWriter pw = new PrintWriter(System.out);
		
		TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);
		
		Test test = new Test();
		
		ClassNode cn = test.addClassInfo();
		
		RemoveMethod rm = new RemoveMethod("t", "()V");
		
		rm.removeMethod(cn);
		
		cn.accept(tcv);
		
		byte[] b = cw.toByteArray();
		
		Class<?> clazz = new MyClassLoader().clazz(b, "com.asm.Example");
		
		System.out.println(clazz.getName());
		
		
	}
	
	public ClassNode addClassInfo() {
		
		ClassNode cn = new ClassNode();
		
		cn.version = Opcodes.V1_8;
		
		cn.access = Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT;
		
		cn.name = "com/asm/Example";
		
		cn.superName = "java/lang/Object";
		
		cn.signature = null;
		
		cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "age", "I", null, 10));
		
		MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC , "print", "()V", null, null);
		
		//mn.instructions 或者使用这个指令集，这是个容器
		
		mn.visitCode();
		
		mn.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		
		mn.visitVarInsn(Opcodes.ILOAD, 1);
		
		mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
		
		mn.visitInsn(Opcodes.RETURN);
		
		mn.visitMaxs(2, 2);
		
		mn.visitEnd();
		
		cn.methods.add(mn);
		
		cn.methods.add(new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "t", "()V", null, null));
		
		MethodNode test = new MethodNode(Opcodes.ACC_PUBLIC , "test", "()V", null, null);
		
		InsnList il = test.instructions;
		
		il.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		
		il.add(new LdcInsnNode("hello_world"));
		
		il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/PrintStream", "println", "(Ljava/lang/String;)V", false));
		
		il.add(new InsnNode(Opcodes.RETURN));
		
		test.maxStack = 2;
		
		test.maxLocals = 1;
		
		cn.methods.add(test);
		
		return cn;
		
	}
	
}
