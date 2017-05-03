package com.asm;

import org.objectweb.asm.ClassVisitor;

public class ClassVisitorAdapter extends ClassVisitor {

	public ClassVisitorAdapter(int api) {
		super(api);
	}

}
