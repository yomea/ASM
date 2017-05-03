package com.asm_core.test;

import java.lang.reflect.Method;

import com.asm_core.logic.AddLogic;

/**
 * 实现了方法逻辑的类
 * @author may
 *
 */
public class AddLogicImpl implements AddLogic {
	
	private Object sayHello;
	
	public AddLogicImpl(Object sayHello) {
		
		this.sayHello = sayHello;
		
	}

	@Override
	public Object addLogic(Method method, Object[] args) throws Exception {
		
		System.out.println("Hello");
		Object obj = method.invoke(sayHello, args);//我们可以在调用目标方法的周围增加逻辑
		System.out.println("baby");
		return obj;
	}

}
