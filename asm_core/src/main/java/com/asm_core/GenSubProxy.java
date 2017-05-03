package com.asm_core;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.TraceClassVisitor;

import com.asm_core.logic.AddLogic;

/**
 * 生成代理对象
 * @author may
 *
 */
public class GenSubProxy {
	
	//逻辑接口
	private AddLogic logic = null;
	//被代理类的所有方法
	private Method[] methods = null;
	
	private String classNamePrefix = null;
	
	private String descInfoPrefix = null;
	
	private String logicPkg = null;
	
	public GenSubProxy(AddLogic logic) {
		
		String logicClassName = AddLogic.class.getName();
		
		this.logicPkg = logicClassName.substring(0, logicClassName.lastIndexOf(AddLogic.class.getSimpleName())).replace(".", "/");
				
		this.logic = logic;
		
	}
	
	
	public Object genSubProxy(Class<?> superClass) {
		
		//获得被代理类的方法集合
		methods = superClass.getDeclaredMethods();
		
		classNamePrefix = superClass.getName().substring(0, superClass.getName().lastIndexOf(superClass.getSimpleName()));
		
		descInfoPrefix = classNamePrefix.replace(".", "/");
		
		Object obj = null;
		try {
			PrintWriter pw = new PrintWriter(System.out, true);
			//生成ClassNode
			ClassNode cn = genClassNode(superClass);
			//ClassWriter.COMPUTE_FRAMES表示让asm自动生成栈图，虽然会慢上二倍。
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			
			TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);
			
			cn.accept(tcv);
			
			//cn.accept(cw);
			
			byte[] b = cw.toByteArray();
			
			MyClassLoader classLoader = new MyClassLoader(b);
			
			Class<?> proxy = classLoader.loadClass(classNamePrefix + "$Proxy");
			
			System.out.println(classNamePrefix + "$Proxy");
			
			obj = proxy.newInstance();
			
			Method method = proxy.getDeclaredMethod("setLogic", AddLogic.class);
			
			method.invoke(obj, logic);
			
			Method meth = proxy.getDeclaredMethod("setMethods", Method[].class);
			 
			meth.invoke(obj, new Object[] {methods});
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public ClassNode genClassNode(Class<?> superClass) {
		
		String superName = superClass.getName().replace(".", "/");
		
		ClassNode cn = new ClassNode(Opcodes.ASM5);
		
	
		//定义代理类的类名
		cn.name = descInfoPrefix + "$Proxy";
		//超类为当前被代理类
		cn.superName = superName;
		
		cn.access = Opcodes.ACC_PUBLIC;
		
		cn.version = Opcodes.V1_8;
		
		cn = proxyMethod(cn);
		
	
		
		
		return cn;
		
	}
	
	@SuppressWarnings("all")
	public ClassNode proxyMethod(ClassNode cn) {
		
		List<MethodNode> list = cn.methods;
		
		List<FieldNode> fields = cn.fields;
		//这里赋初始值必须是Integer, Float, Long, Double 或者 String，null
		fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "logic", "L" + logicPkg + "AddLogic;", null, null));
		//添加methods字段
		fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "methods", "[Ljava/lang/reflect/Method;", null, null));
		//添加方法setLogic，用于设置Logic
		MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "setLogic", "(L" + logicPkg + "AddLogic;)V", null, null);
		//下面的指令相当于this.logic = logic;
		InsnList insnList = mn.instructions;
		
		insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
		
		insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, descInfoPrefix + "$Proxy", "logic", "L" + logicPkg + "AddLogic;"));
		
		insnList.add(new InsnNode(Opcodes.RETURN));
		
		mn.maxLocals = 2;//定义最大的本地变量
		
		mn.maxStack = 2;//定义最大的操作数栈
		
		list.add(mn);
		//添加一个setMethods方法，用于设置methods字段
		MethodNode meth = new MethodNode(Opcodes.ACC_PUBLIC, "setMethods", "([Ljava/lang/reflect/Method;)V", null, null);
		//这段指令相当于this.methods = methods;
		InsnList methList = meth.instructions;
		
		methList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		methList.add(new VarInsnNode(Opcodes.ALOAD, 1));
		
		methList.add(new FieldInsnNode(Opcodes.PUTFIELD, descInfoPrefix + "$Proxy", "methods", "[Ljava/lang/reflect/Method;"));
		
		methList.add(new InsnNode(Opcodes.RETURN));
		
		meth.maxLocals = 2;
		
		meth.maxStack = 2;
		
		list.add(meth);//
		//添加构造方法
		MethodNode init = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		//这里是调用父类构造器，相当于super();
		InsnList initList = init.instructions;
		
		initList.add(new VarInsnNode(Opcodes.ALOAD, 0));
		
		initList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, descInfoPrefix + "SayHello", "<init>", "()V", false));
		
		initList.add(new InsnNode(Opcodes.RETURN));
		
		init.maxLocals = 1;
		
		init.maxStack = 1;
		
		list.add(init);
		
		int count = 0;
		//循环创建需要代理的方法
		for (Method method : methods) {
			
			MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC,  method.getName(), DescInfo.getDescInfo(method), null, null);
			
//			System.err.println(DescInfo.getDescInfo(method));
			
			InsnList il = methodNode.instructions;
			//获得这个方法的参数个数，不包括this
			int len = method.getParameterTypes().length;
			//获得参数的类型
			Class<?>[] clazz = method.getParameterTypes();
			
			Class<?> rtClazz = method.getReturnType();
			
			il.clear();
			/**
			 * 下面的一大段指令的意思是
			 * int index = count;
			 * Method method = methods[index];//从methods中获得对应的方法对象
			 * Object[] objs = new Object[]{arg0,arg1,arg2....};用来存方法传过来的参数值的
			 * try {
			 * 		logic.addLogic(method, objs);
			 * } catch(Exception e) {
			 * 		e.printStackTrace();
			 * }
			 */
			il.add(new LdcInsnNode(count));//
			
			il.add(new VarInsnNode(Opcodes.ISTORE, len + 1));
			
			il.add(new VarInsnNode(Opcodes.ALOAD, 0));
			
			il.add(new FieldInsnNode(Opcodes.GETFIELD, descInfoPrefix + "$Proxy", "methods", "[Ljava/lang/reflect/Method;"));
			
			il.add(new VarInsnNode(Opcodes.ILOAD, len + 1));
			
			il.add(new InsnNode(Opcodes.AALOAD));
			
			il.add(new VarInsnNode(Opcodes.ASTORE, len + 2));//将栈顶的method存到局部变量表中
			
			//il.add(new LdcInsnNode(len));//将参数长度推到栈顶
			il.add(new LdcInsnNode(len));
			
			il.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));//new 出一个Object的数组
			
			il.add(new VarInsnNode(Opcodes.ASTORE, len + 3));//将数组存到本地变量表中
			//将参数值全都存到数组中
			for(int i = 0; i < len; i++) {
				
				il.add(new VarInsnNode(Opcodes.ALOAD, len + 3));//将数组推到栈顶
				
				il.add(new LdcInsnNode(i));//下标
				
				int opcode = OpcodeMap.getOpcodes(clazz[i].getName());//获得当前是什么类型的参数，使用什么样类型的指令
				
				il.add(new VarInsnNode(opcode, i+1));//将参数推到栈顶
				
				if(AutoPKG.auto(clazz[i].getName()) != null) {
					
					il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, AutoPKG.auto(clazz[i].getName()), "valueOf", AutoPKG_valueOf.auto(clazz[i].getName()), false));
					
				}
				
				
				il.add(new InsnNode(Opcodes.AASTORE));//将数据存到数组中
				
			}
			
			
			il.add(new VarInsnNode(Opcodes.ALOAD, 0));//
			
			il.add(new FieldInsnNode(Opcodes.GETFIELD, descInfoPrefix + "$Proxy", "logic", "L" + logicPkg + "AddLogic;"));
			
			il.add(new VarInsnNode(Opcodes.ALOAD, len+2));
			
			il.add(new VarInsnNode(Opcodes.ALOAD, len + 3));
			
			il.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "" + logicPkg + "AddLogic", "addLogic", "(Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", true));
			
			il.add(new TypeInsnNode(Opcodes.CHECKCAST, rtClazz.getName().replace(".", "/")));
			
			LabelNode label = new LabelNode();
			
			il.add(new JumpInsnNode(Opcodes.GOTO, label));
			//由于对栈图还是不太明白是啥意思，如果有知道的麻烦告知我一声
			//il.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			
			il.add(new VarInsnNode(Opcodes.ASTORE, len + 4));
			
			il.add(new VarInsnNode(Opcodes.ALOAD, len + 4));
			
			il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false));
			
			il.add(label);
			
			il.add(new InsnNode(OpcodeRt.getOpcodes(rtClazz.getName())));
			
			methodNode.maxLocals = 5+len;
			
			methodNode.maxStack = 3;
			
			list.add(methodNode);
			
			count ++;
		}
		
		return cn;
	}
	
	

}
