package com.asm;

public class MyClassLoader extends ClassLoader {

	public Class<?> clazz(byte[] buff) {
		
		
		return this.defineClass("pkg.Comparable", buff, 0, buff.length);
	}
	

}
