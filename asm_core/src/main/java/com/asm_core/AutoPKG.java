package com.asm_core;

import java.util.HashMap;
import java.util.Map;

public class AutoPKG {
	
	public static final Map<String, String> map = new HashMap<>();
	
	static {
		map.put("int", "java/lang/Integer");
		
		map.put("byte", "java/lang/Byte");
		
		map.put("short", "java/lang/Short");
		
		map.put("long", "java/lang/Long");
		
		map.put("boolean", "java/lang/Boolean");
		
		map.put("char", "java/lang/Character");
		
		map.put("float", "java/lang/Float");
		
		map.put("double", "java/lang/Double");
		
	}
	
	public static String auto(String type) {
		
		if(map.containsKey(type)) {
			
			return map.get(type);
		} else {
			
			
			return null;
		}
		
		
	}

}
