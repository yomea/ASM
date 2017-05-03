package com.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class Test {
	
	public static void main(String[] args) {
		
		byte[] buff = ClassVisitorClass.createClassByte();
		
		ClassReader cr = new ClassReader(buff);
		
		//交给ClassVisitorAdapter处理，这个adapter可以自己定义一些逻辑
		cr.accept(new ClassVisitorAdapter(Opcodes.ASM5), 0);
		
	}

}
