package com.asm_core;

public class MyClassLoader extends ClassLoader {
	
	private byte[] b = null;
	
	public MyClassLoader(byte[] b) {
		
		this.b = b;
		
	}
	
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		return this.defineClass(name, b, 0, b.length);
	}

}
