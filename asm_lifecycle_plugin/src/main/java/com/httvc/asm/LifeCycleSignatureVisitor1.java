package com.httvc.asm;

import org.objectweb.asm.signature.SignatureVisitor;

/**
 * SignatureVisitor用来访问泛型类型的
 * 类签名 ClassSignature
 * 放法签名 MethodSignature
 * 域签名 FieldSignature
 */
public class LifeCycleSignatureVisitor1 extends SignatureVisitor {

    public LifeCycleSignatureVisitor1(int api) {
        super(api);
    }

    /**
     * 访问正规类型参数
     * @param name
     */
    @Override
    public void visitFormalTypeParameter(String name) {
        super.visitFormalTypeParameter(name);
    }

    /**
     * 访问最后一个被访问的正规类型参数的类界限
     * @return
     */
    @Override
    public SignatureVisitor visitClassBound() {
        return super.visitClassBound();
    }

    /**
     * 访问最后一个被访问的正规类型参数的接口界限
     * @return
     */
    @Override
    public SignatureVisitor visitInterfaceBound() {
        return super.visitInterfaceBound();
    }

    /**
     * 访问该类型的超类
     * @return
     */
    @Override
    public SignatureVisitor visitSuperclass() {
        return super.visitSuperclass();
    }

    /**
     * 访问该类所实现的接口
     * @return
     */
    @Override
    public SignatureVisitor visitInterface() {
        return super.visitInterface();
    }

    /**
     * 访问方法参数类型
     * @return
     */
    @Override
    public SignatureVisitor visitParameterType() {
        return super.visitParameterType();
    }

    /**
     * 访问方法返回值类型
     * @return
     */
    @Override
    public SignatureVisitor visitReturnType() {
        return super.visitReturnType();
    }

    /**
     * 访问方法异常类型
     * @return
     */
    @Override
    public SignatureVisitor visitExceptionType() {
        return super.visitExceptionType();
    }

    /**
     * 访问基本类型的签名
     * @param descriptor
     */
    @Override
    public void visitBaseType(char descriptor) {
        super.visitBaseType(descriptor);
    }

    /**
     * 访问类型变量的签名
     * @param name
     */
    @Override
    public void visitTypeVariable(String name) {
        super.visitTypeVariable(name);
    }

    /**
     * 访问一个数组类型的签名
     * @return
     */
    @Override
    public SignatureVisitor visitArrayType() {
        return super.visitArrayType();
    }

    /**
     * 开始访问类或者接口类型的签名
     * @param name
     */
    @Override
    public void visitClassType(String name) {
        super.visitClassType(name);
    }

    /**
     * 访问内部类
     * @param name
     */
    @Override
    public void visitInnerClassType(String name) {
        super.visitInnerClassType(name);
    }

    /**
     *访问最后被访问类或者接口的无界限类型参数
     */
    @Override
    public void visitTypeArgument() {
        super.visitTypeArgument();
    }
    /**
     * 访问最后被访问类或者内部类的类型参数
     * @param wildcard
     * @return
     */
    @Override
    public SignatureVisitor visitTypeArgument(char wildcard) {
        return super.visitTypeArgument(wildcard);
    }

    /**
     * 访问结束通知
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
