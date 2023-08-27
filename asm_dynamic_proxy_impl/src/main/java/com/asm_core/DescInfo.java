package com.asm_core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于生成字节码描述符的工具类
 * @author may
 *
 */
public class DescInfo {
	
	public static final Map<String, String> map = new HashMap<>();
	
	static {
		
		map.put("int", "I");
		
		map.put("byte", "B");
		
		map.put("short", "S");
		
		map.put("long", "J");
		
		map.put("boolean", "Z");
		
		map.put("char", "C");
		
		map.put("float", "F");
		
		map.put("double", "D");
		
		map.put("void", "V");
		
	}
	
	public static String getDescInfo(Method method) {
		
		Class<?>[]  pt = method.getParameterTypes();
		
		Class<?>  rt = method.getReturnType();
		
		String rtStr = "V";
		
		if(map.containsKey(rt.getName())) {
			
			rtStr = map.get(rt.getName());
			
		} else {
			/**
			 * 如果不为空，那么就是数组
			 */
			Class<?> clazz= rt.getComponentType();//如果原来的rt是int[][]
			Class<?> oldClazz = clazz;//int[]
			int count = 0;
			if(oldClazz != null) {
				rtStr = "";
				while(clazz != null) {
					count ++;//2
					oldClazz = clazz;
					clazz= clazz.getComponentType();
				}
				for(int i = 0; i < count; i++) {
					rtStr += "[";
					
				}
				if(map.containsKey(oldClazz.getName())) {
					rtStr += map.get(oldClazz.getName());
				} else {
					rtStr += "L" + oldClazz.getName().replace(".", "/") + ";";
				}
			} else {
				
				rtStr = "L" + rt.getName().replace(".", "/") + ";";
			}
		}
		
		
		String descInfo = "(";
		
		for (Class<?> class1 : pt) {
			String name = class1.getName();
			
			if(map.containsKey(name)) {
				descInfo += map.get(name);
				
			} else {
				if(class1.getComponentType() != null) {
					descInfo += class1.getName().replace(".", "/");
					
				} else {
					
					descInfo += ("L" + name.replace(".", "/") + ";");
				}
			}
			
		}
		descInfo += ")" + rtStr;
		return descInfo;
		
	}
	

}
