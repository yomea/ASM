package com.asm_core;

/**
 * 计算本地变量表的长度，long，double类型会占用两个slot
 * @author may
 *
 */
public class LocalLen {
	
	
	public static int len(Class<?>[] clzz) {
		
		int count = 0;
		
		for (Class<?> class1 : clzz) {
			
			String str = class1.getName();
			if(str.equals("long") || str.equals("double")) {
				
				count += 2;
				
			} else {
				
				count ++;
			}
		}
		
		return count;
	}
	
}
