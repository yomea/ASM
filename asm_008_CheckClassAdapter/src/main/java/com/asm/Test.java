package com.asm;

import java.io.PrintWriter;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.CheckClassAdapter;
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
		//
		
		
		//这个类存在与util包中，用来打印生成class的轨迹信息
		TraceClassVisitor tcv = new TraceClassVisitor(cw, sw);
		//对输入值得信息进行检查
		CheckClassAdapter cca = new CheckClassAdapter(tcv);
		
		//也可以new TraceClassVisitor（new CheckClassAdapter），因为它们都是使用的聚合方式的静态代理
		
		cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Test", null, "java/lang/Number", null);
		//int access, String name, String desc,
		//            String signature, Object value
		cca.visitField(Opcodes.ACC_PRIVATE, "nameList", "Ljava/util/List;", null, null);
		cca.visitField(Opcodes.ACC_PRIVATE, "ageList", "Ljava/util/List;", null, null);

		cca.visitEnd();
		
		byte[] b = cw.toByteArray();
		
		Class<?> clazz = new MyClassLoader().clazz(b, "Test");
		
		System.out.println(clazz.getName());
		
	}
	
}
