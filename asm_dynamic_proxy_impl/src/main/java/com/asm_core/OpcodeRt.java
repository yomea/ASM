package com.asm_core;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;

public class OpcodeRt {

	public static Map<String, Integer> map = new HashMap<>();
	
	static {
		
		
		map.put("int", Opcodes.IRETURN);
		
		map.put("byte", Opcodes.IRETURN);
		
		map.put("short", Opcodes.IRETURN);
		
		map.put("long", Opcodes.LRETURN);
		
		map.put("boolean", Opcodes.IRETURN);
		
		map.put("char", Opcodes.IRETURN);
		
		map.put("float", Opcodes.FRETURN);
		
		map.put("double", Opcodes.DRETURN);
		
		map.put("void", Opcodes.RETURN);
		
		
	}
	
	public static int getOpcodes(String type) {
		
		if(map.containsKey(type)) {
			
			return map.get(type);
			
		} else {
			
			
			return Opcodes.ARETURN;
		}
		
	}
	
}
