package com.asm;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * TraceClassVisitor
 * 主要用来打印生成class的信息
 * 
// class version 52.0 (52)
// access flags 0x1
public class Test extends java/lang/Number  {


  // access flags 0x1
  public <init>()V
    ALOAD 0
    INVOKESPECIAL java/lang/Number.<init> ()V
    RETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x1
  public print(I)V
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    ILOAD 1
    INVOKEVIRTUAL java/io/PrintStream.println (I)V
    RETURN
    MAXSTACK = 2
    MAXLOCALS = 2
}

public class Test extends java/lang/Number  {


  public Test() {
   		super();
   }

   public void print(int age) {
    	System.out.println(age);
	}

	public void test(int age) {
	     if(age > 12) {
			 System.out.println("。。。。。你懂的");
			
		 } else {
			 throw new RuntimeException("少儿不宜");
			 
		 }
	}

}
 * 
 * 
 * 1、如果要生成方法的代码，需要先以visitCode开头，访问结束需要调用visitEnd方法；
	2、方法都是在线程中执行的，每个线程有自己的虚拟机栈，这个栈与线程同时创建，用于存储栈帧。栈帧随着方法调用而创建，方法结束时销毁。每个栈帧都有自己的本地变量表、操作数栈和指向常量池的引用。
		本地变量表和操作数栈的大小在编译期确定，在asm中可以通过visitMaxs来指定本地变量表与操作数栈的大小。visitFrame方法可以指定栈帧中的本地变量与操作数。
		对本地变量和操作数栈的大小设置受ClassWriter的flag取值影响：
（1）new ClassWriter(0),表明需要手动计算栈帧大小、本地变量和操作数栈的大小；
（2）new ClassWriter(ClassWriter.COMPUTE_MAXS)需要自己计算栈帧大小，但本地变量与操作数已自动计算好，当然也可以调用visitMaxs方法，只不过不起作用，参数会被忽略；
（3）new ClassWriter(ClassWriter.COMPUTE_FRAMES)栈帧本地变量和操作数栈都自动计算，不需要调用visitFrame和visitMaxs方法，即使调用也会被忽略。
		这些选项非常方便，但会有一定的开销，使用COMPUTE_MAXS会慢10%，使用COMPUTE_FRAMES会慢2倍。
3、visitInsn、visitVarInsn、visitMethodInsn等以Insn结尾的方法可以添加方法实现的字节码。
 * 
 * @author may
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
		//COMPUTE_FRAMES,指定这个会自动计算frames ，但是最后还是要调用visitMAXS，可以传入合法参数，但是没有用，它会自动计算stack和locals的大小
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		
		PrintWriter sw = new PrintWriter(System.out);
		//
		
		
		//这个类存在与util包中，用来打印生成class的轨迹信息
		TraceClassVisitor tcv = new TraceClassVisitor(cw, sw);
		//对输入值得信息进行检查
		CheckClassAdapter cca = new CheckClassAdapter(tcv);
		
		//也可以new TraceClassVisitor（new CheckClassAdapter），因为它们都是使用的聚合方式的静态代理
		
		cca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Test", null, "java/lang/Number", null);
		
		//生成构造方法，固定的名字<init>
		MethodVisitor constrMV = cca.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		
		constrMV.visitCode();
		//将this压入操作数栈
		constrMV.visitVarInsn(Opcodes.ALOAD, 0);
		//调用父类构造器，并弹出操作数栈的值作为参数传入
		constrMV.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Number", "<init>", "()V", false);
		//返回
		constrMV.visitInsn(Opcodes.RETURN);
		//指定最大的操作数栈和最大的本地变量表
		constrMV.visitMaxs(2, 2);
		
		constrMV.visitEnd();
		
		MethodVisitor mv = cca.visitMethod(Opcodes.ACC_PUBLIC, "print", "(I)V", null, null);
		
		//Starts the visit of the method's code, if any (i.e. non abstract method).
		mv.visitCode();
		//可以指定本地变量，和操作数栈
		//mv.visitFrame(type, nLocal, local, nStack, stack);
		
		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
		
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();
		
		
		MethodVisitor test = cca.visitMethod(Opcodes.ACC_PUBLIC, "test", "(I)V", null, null);
		
		//Starts the visit of the method's code, if any (i.e. non abstract method).
		test.visitCode();
		
		test.visitVarInsn(Opcodes.ILOAD, 1);
		
		test.visitIntInsn(Opcodes.BIPUSH, 12);
		
		Label label = new Label();
		
		test.visitJumpInsn(Opcodes.IF_ICMPLE, label);
		
		test.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		
		test.visitLdcInsn("。。。。。你懂的");
		
		test.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		
		Label end = new Label();
		//跳转语句goto
		test.visitJumpInsn(Opcodes.GOTO, end);
		
		test.visitLabel(label);
		
		test.visitTypeInsn(Opcodes.NEW, "java/lang/RuntimeException");
		
		test.visitInsn(Opcodes.DUP);
		
		test.visitLdcInsn("少儿不宜");
		
		test.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V", false);
		
		test.visitInsn(Opcodes.ATHROW);
		
		//test.visitFrame(type, nLocal, local, nStack, stack);
		
		test.visitLabel(end);
		test.visitInsn(Opcodes.RETURN);
		
		test.visitMaxs(3, 2);
		
		test.visitEnd();
		
		cca.visitEnd();
		
		byte[] b = cw.toByteArray();
		Class<?> clazz = new MyClassLoader().clazz(b, "Test");
		
		Method m = clazz.getMethod("print", int.class);
		
		Object obj = clazz.newInstance();
		
		m.invoke(obj, 10);
		
		Method mm = clazz.getMethod("test", int.class);
		
		mm.invoke(obj, 20);
		
	}
	
}
