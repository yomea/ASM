package com.asm;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * TraceClassVisitor
 * 主要用来打印生成class的信息
 * @author may
 *
 */
public class Test {

	public static void main(String[] args) {
		
		ClassWriter cw = new ClassWriter(0);
		
		PrintWriter sw = new PrintWriter(System.out);
		
		//这个类存在与util包中，用来打印生成class的轨迹信息
		TraceClassVisitor tcv = new TraceClassVisitor(cw, sw);
		
		tcv.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Test", null, "java.lang.Object", null);
		
		tcv.visitEnd();
		
		byte[] b = cw.toByteArray();
		
	}
	
}
