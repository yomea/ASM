package com.asm;

import org.objectweb.asm.ClassReader;

public class Test {

	public static void main(String[] args) throws Exception {
		ClassPrinter cp = new ClassPrinter();
		//读取一个java.lang.Runnable的class文件，进行解析
		ClassReader cr = new ClassReader("com.asm.Person");
		cr.accept(cp, 0);
	}

}
