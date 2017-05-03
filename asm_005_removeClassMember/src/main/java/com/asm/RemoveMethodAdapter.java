package com.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RemoveMethodAdapter extends ClassVisitor implements Opcodes {

	private String mName;
	private String mDesc;

	public RemoveMethodAdapter(ClassVisitor cv, String mName, String mDesc) {
		super(ASM4, cv);
		this.mName = mName;
		this.mDesc = mDesc;

	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		if (name.equals(mName) && desc.equals(mDesc)) {
			// do not delegate to next visitor -> this removes the method
			return null;
		}
		return cv.visitMethod(access, name, desc, signature, exceptions);
	}

}
