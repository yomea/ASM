package com.asm_core.logic;

import java.lang.reflect.Method;

/**
 * 这个类用来增加方法逻辑,类似于JDK代理的InvocationHandler
 * @author may
 *
 */
public interface AddLogic {
	
	/**
	 * 
	 * @param method 被代理对象的方法对象
	 * @param args 被代理方法的参数
	 * @throws Exception
	 */
	public Object addLogic(Method method, Object[] args) throws Exception ;

}
