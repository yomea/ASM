package com.asm_core.test;

import com.asm_core.GenSubProxy;
import com.asm_core.logic.AddLogic;

public class Test {
	
public static void main(String[] args) {
		
		SayHello sayHello = new SayHello();
		
		AddLogic logic = new AddLogicImpl(sayHello);
		
		GenSubProxy genSubProxy = new GenSubProxy(logic);
		
		Object obj = genSubProxy.genSubProxy(SayHello.class);
		
		SayHello sh = (SayHello) obj;
		
		sh.sayHello("sdgf", "dfg", 22, new String[]{"sdfg"});
		
	}

}
