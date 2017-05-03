package com.asm;

import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class RemoveMethod {

	private String name;
	private String desc;
	
	public RemoveMethod(String name, String desc) {
		this.name = name;
		this.desc = desc;
		
	}

	public void removeMethod(ClassNode cn) {
		
		List<MethodNode> methodNodes = cn.methods;
		
		Iterator<MethodNode> it = methodNodes.iterator();
		
		while(it.hasNext()) {
			MethodNode methodNode = it.next();
			
			if(methodNode.name.equals(this.name) && methodNode.desc.equals(this.desc)) {
				
				it.remove();
				
			}
			
		}
		
	}
	
}
