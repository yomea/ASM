package com.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor {

	public ClassPrinter() {
		//api the ASM API version implemented by this visitor
		super(Opcodes.ASM5);
	}

	//定义一个类
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		System.out.println(name + " extends " + superName + " {");
	}

	public void visitSource(String source, String debug) {
		
		System.err.println("visitSource");
		
	}

	public void visitOuterClass(String owner, String name, String desc) {
		System.err.println("visitOuterClass");
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		System.err.println("visitAnnotation");
		return null;
	}

	public void visitAttribute(Attribute attr) {
		System.err.println("visitAttribute");
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		System.err.println("visitInnerClass");
	}
	
	//这个类有字段，那么就会打印出字段的描述符和字段名
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		System.out.println(" " + desc + " " + name);
		return null;
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		System.out.println(" " + name + desc);
		return null;
	}

	public void visitEnd() {
		System.out.println("}");
	}

}
