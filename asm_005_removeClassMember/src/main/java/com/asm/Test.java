package com.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * ClassWriter是一个继承了ClassVisitor的类，
 * 
 * ClassVisitor是个抽象类，但是内部有个ClassVisitor的字段，它的每个方法
 * 都是调用这个字段的方法，所以这是个变相的接口
 * @author may
 *
 */
public class Test {
	
	public static void main(String[] args) throws Exception {
		
		ClassReader cr = new ClassReader("com.asm.Person");
		
		ClassWriter cw = new ClassWriter(0);
		
		RemoveMethodAdapter rma = new RemoveMethodAdapter(cw, "setAge", "(I)V");
		
		cr.accept(rma, 0);
		
		byte[] b = cw.toByteArray();
		
		Class<?> clazz = new MyClassLoader().clazz(b, "com.asm.Person");
		
		Method[] methods = clazz.getDeclaredMethods();
		
		/**
		 * getName
		 * setName
		 * getSex
		 * setSex
		 * getAge
		 * 没有了setAge方法
		 */
		for (Method method : methods) {
			System.out.println(method.getName());
		}
		
	}

}
