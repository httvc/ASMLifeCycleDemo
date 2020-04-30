package com.httvc.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

/**
 * MethodVisitor是用来在访问类的方法字节码过程中创建方法或者修改方法字节码信息从而实现该边方法
 * 访问顺序
 * visitParameter  可以多次调用
 * visitAnnotationDefault 最多调用一次
 * visitAnnotation,visitAnnotableParameterCount,visitParameterAnnotation,visitTypeAnnotation,visitAttribute 可以顺序多次访问
 * visitCode最多调用一次
 * visitFrame,visitInsn,visitLabel,visitlnsnAnnotation,visitTryCatchBlock,visitTryCatchAnnotation,
 * visitLocalVariable,visitLocalVariableAnnotation,visitLineNumber  可以顺序多次调用
 * visitMaxs 最多调用一次
 * visitEnd 访问结束时必须访问一次
 */
public class LifecycleMethodVisitor1 extends MethodVisitor {
    private String className;
    private String methodName;

    public LifecycleMethodVisitor1(MethodVisitor methodVisitor,String className,String methodName) {
        super(Opcodes.ASM5,methodVisitor);
        this.className=className;
        this.methodName=methodName;
    }

    /**
     * 访问方法一个参数
     * @param name 参数名称
     * @param access 参数访问类型，如final，synthetick，MANDATED；
     */
    @Override
    public void visitParameter(String name, int access) {
        super.visitParameter(name, access);
    }

    /**
     * 访问注解接口方法的默认值
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return super.visitAnnotationDefault();
    }

    /**
     * 访问方法的一个注解
     * @param descriptor 表示注解类的描述，即注解类的描述：如Ljava/lang/JavaBean等
     * @param visible 表示该注解运行时是否可见
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

    /**
     * 访问方法签名上的一个类型的注解
     * @param typeRef
     * @param typePath
     * @param descriptor
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    /**
     * 访问注解参数数量，就是访问方法参数有注解参数个数
     * @param parameterCount
     * @param visible
     */
    @Override
    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
        super.visitAnnotableParameterCount(parameterCount, visible);
    }

    /**
     * 访问参数的注解，返回一个AnnotationVisitor可以访问该注解值
     * @param parameter
     * @param descriptor
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
        return super.visitParameterAnnotation(parameter, descriptor, visible);
    }

    /**
     * 访问方法属性
     * @param attribute
     */
    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    /**
     * 开始访问方法代码，此处可以添加方法运行前拦截器
     */
    @Override
    public void visitCode() {
        super.visitCode();
    }

    /**
     * 访问方法局部变量的当前状态以及操作栈成员信息，方法栈必须是expanded格式
     * 或者compressed格式，该方法必须在visitInsn方法前调用
     * @param type
     * @param numLocal
     * @param local
     * @param numStack
     * @param stack
     */
    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        super.visitFrame(type, numLocal, local, numStack, stack);
    }

    /**
     * 访问数值类型指令
     * @param opcode  表示操作码指令，在这里opcode可以是Opcodes.BIPUSH,Opcodes.SIPUSH,Opcodes.NEWARRAY中一个
     * @param operand 表示操作数
     *                如果opcode为BIPUSH，那么operand value必须在Byte.minValue和Byte.maxValue之间
     *                如果opcode为SIPUSH，那么operand value必须在Short.minValue和Short.minValue之间
     *                如果opcode为NEWARRAY，那么operand value 可以取下面中一个：
     *                Opcodes.T_BOOLEN,OPcodes.T_BYTE,OPCODES.T_CHAR,OPcodes.T_SHORT,OPcodes.T_INT,
     *                Opcodes.T_FLOAT,Opcodes.T_DOUBLE,Opcodes.T_LONG
     */
    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
    }

    /**
     * 访问本地变量类型指令
     * @param opcode  操作码可以是LOAD,STORE,RET中一种
     * @param var    表示需要访问的变量
     */
    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
    }

    /**
     * 访问类型指令
     * 类型指令会把类的内部名称当成参数Type
     * @param opcode 操作码为NEW,ANEWARRAY,CHECKCAST,INSTANCEOF
     * @param type   对象或者数组的内部名称，可以通过Type.getInternalName()获取；
     */
    @Override
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
    }

    /**
     * 域操作指令，用来加载或者存储对象的Field
     * @param opcode
     * @param owner
     * @param name
     * @param descriptor
     */
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    /**
     * 访问方法操作指令
     * @param opcode 为INVOKESPECIAL,INVOKESTATIC,INVOKEVIRTUAL,INVOKEINTERFACE
     * @param owner  方法拥有者的名称
     * @param name    方法名称
     * @param descriptor 方法描述，参数和返回值
     * @param isInterface 是否是接口
     */
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    /**
     * 访问动态类型指令
     * @param name
     * @param descriptor
     * @param bootstrapMethodHandle
     * @param bootstrapMethodArguments
     */
    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    /**
     * 访问比较跳转指令
     * @param opcode IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,
     *               IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,IFNULL or IFNONNULL
     * @param label 跳转目的label
     */
    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
    }

    /**
     * 访问label 当前在调用该方法后访问该label标记一个指令
     * @param label
     */
    @Override
    public void visitLabel(Label label) {
        super.visitLabel(label);
    }

    /**
     * 访问ldc指令，也就是访问常量池索引
     * @param value 必须是非空的Integere,Float,Double,Long,String,或者对象的Type,Method sort的Type，
     *              或者Method常量中的Handle，或者ConstantDynamic
     */
    @Override
    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(value);
    }

    /**
     * 访问跳转指令
     * @param min
     * @param max
     * @param dflt
     * @param labels
     */
    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    /**
     * 访问查询跳转指令
     * @param dflt
     * @param keys
     * @param labels
     */
    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    /**
     * 访问多维数组指令
     * @param descriptor
     * @param numDimensions
     */
    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    /**
     * 访问指令注解，必须在访问注解之后调用
     * @param typeRef
     * @param typePath
     * @param descriptor
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
    }

    /**
     * 方法try--catch块
     * @param start
     * @param end
     * @param handler
     * @param type
     */
    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type);
    }

    /**
     * 访问try...catch块上异常处理的类型注解，必须在调用visitTryCatchBlock之后调用
     * @param typeRef
     * @param typePath
     * @param descriptor
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
    }

    /**
     * 访问本地变量描述
     * @param name         本地变量名称
     * @param descriptor   本地变量类型描述
     * @param signature    本地变量类型签名
     * @param start        关联该本地变量范围的第一个指令的位置
     * @param end           关联该本地变量范围的最后一个指令的位置
     * @param index         本地变量的索引
     */
    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

    /**
     * 访问行号描述
     * @param line
     * @param start
     */
    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
    }

    /**
     * 访问操作数栈最大值和本地变量表最大值
     * @param maxStack
     * @param maxLocals
     */
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }


    /**
     * 域访问完成后必须调用
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
