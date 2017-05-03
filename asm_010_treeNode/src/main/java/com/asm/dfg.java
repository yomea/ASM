package com.asm;
 
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
/**
 */
public class dfg {
 
    public static void main(String[] args) throws FileNotFoundException {
        ClassWriter cw = new ClassWriter(Opcodes.ASM5);
        ClassNode cn = gen();
        cn.accept(cw);
        File file = new File("ChildClass.class");
        FileOutputStream fout = new FileOutputStream(file);
        try {
            fout.write(cw.toByteArray());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
 /**
  * 想要删除某个方法，啥的就直接删了对应容器中的就好了
  * @return
  */
    private static ClassNode gen() {
        ClassNode classNode = new ClassNode();
        classNode.version = Opcodes.V1_8;
        classNode.access = Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT;
        classNode.name = "asm/core/ChildClass";
        classNode.superName = "java/lang/Object";
        classNode.interfaces.add("asm/core/ParentInter");
        classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "zero", "I",
                null, new Integer(0)));
        classNode.methods.add(new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null));
        classNode.methods.add(new MethodNode(Opcodes.ACC_PUBLIC+ Opcodes.ACC_ABSTRACT, "print", "()V", null, null));
        return classNode;
        
        
 
    }
}
 