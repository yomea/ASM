package com.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class AddMethodVisitor extends ClassVisitor {

	public AddMethodVisitor(int api, ClassVisitor cv) {
		super(api, cv);
	}
	
	@Override
	public void visitEnd() {
		cv.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT, "print", "()V", null, new String[]{}).visitEnd();;
		//cv.visitField(Opcodes.ACC_PUBLIC, "color", "I", null, null).visitEnd();
		cv.visitEnd();
	}

}
