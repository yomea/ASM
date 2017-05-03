package com.asm_core;

import java.util.HashMap;
import java.util.Map;

public class AutoPKG_valueOf {
	
	public static final Map<String, String> map = new HashMap<>();
	
	static {
		map.put("int", "(I)Ljava/lang/Integer;");
		
		map.put("byte", "(B)Ljava/lang/Byte;");
		
		map.put("short", "(S)Ljava/lang/Short;");
		
		map.put("long", "(J)Ljava/lang/Long;");
		
		map.put("boolean", "(Z)Ljava/lang/Boolean;");
		
		map.put("char", "(C)Ljava/lang/Character;");
		
		map.put("float", "(F)Ljava/lang/Float");
		
		map.put("double", "(D)Ljava/lang/Double;");
		
	}
	
	public static String auto(String type) {
		
		if(map.containsKey(type)) {
			
			return map.get(type);
		} else {
			
			return null;
		}
		
		
	}

}
