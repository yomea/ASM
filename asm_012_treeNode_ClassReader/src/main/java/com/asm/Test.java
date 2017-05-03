package com.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

/**
 * 
 * @author may
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
			
		ClassReader cr = new ClassReader("com.asm.MyClassLoader");
		
		ClassNode cn = new ClassNode(Opcodes.ASM5);
		
		cr.accept(cn, 0);//读取cr中值到cn中
		
		ClassWriter cw = new ClassWriter(0);
		
		cn.accept(cw);
		
		byte[] b = cw.toByteArray();
		
		Class<?> clazz = new MyClassLoader().clazz(b, "com.asm.MyClassLoader");
		
		System.out.println(clazz);
		
		
	}
	
	
}
