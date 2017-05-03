package com.asm;

public class MyClassLoader extends ClassLoader {

	public Class<?> clazz(byte[] buff,String name) {
		
		
		return this.defineClass(name, buff, 0, buff.length);
	}
	

}
