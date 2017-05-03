package com.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.signature.SignatureWriter;

/**
 * package pkg;
	public interface Comparable extends Mesurable {
		int LESS = -1;
		int EQUAL = 0;
		int GREATER = 1;
		int compareTo(Object o);
	}
 * @author may
 *
 */
public class GeneratClass implements Opcodes {

	public static void main(String[] args) throws Exception {

		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "pkg/Comparable", "<T:Ljava/lang/Object;>Ljava/lang/Object;", "java/lang/Object",
				new String[] { "pkg/Mesurable" });
		cw.visitAnnotation("Ljava/lang/Deprecated", true);
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, new Integer(-1)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, new Integer(0)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, new Integer(1)).visitEnd();
		cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();
		byte[] b = cw.toByteArray();
		
		Class<?> clazz =new MyClassLoader().clazz(b);
		//如果取得静态的变量就没必要传对象进去
		int obj = clazz.getField("LESS").getInt(null);
		
		System.out.println(obj);//-1
		
		
		String s = "Ljava/util/HashMap<TK;TV;>.HashIterator<TK;>;";
		Map<String, String> renaming = new HashMap<String, String>();
		renaming.put("java/util/HashMap", "A");
		renaming.put("java/util/HashMap.HashIterator", "B");
		SignatureWriter sw = new SignatureWriter();
		SignatureVisitor sa = new RenameSignatureAdapter(sw, renaming);
		SignatureReader sr = new SignatureReader(s);
		sr.acceptType(sa);
		;
		System.out.println(sw.toString());

	}

}
