package com.httvc.asm;

import org.objectweb.asm.AnnotationVisitor;

/**
 * AnnotationVisitor是用来访问Annotation的
 * visit,visitEnum,visitAnnotation,code visitArray 可以访问多次
 * visitEnd 只能访问一次
 */
public class LifeCycleAnnotationVisitor1 extends AnnotationVisitor {
    public LifeCycleAnnotationVisitor1(int api) {
        super(api);
    }

    /**
     * 访问注解的基本值
     * @param name
     * @param value
     */
    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
    }

    /**
     * 访问注解的枚举类型值
     * @param name
     * @param descriptor
     * @param value
     */
    @Override
    public void visitEnum(String name, String descriptor, String value) {
        super.visitEnum(name, descriptor, value);
    }

    /**
     * 访问嵌套注解类型，也就是一个注解可能被其他注解所注释
     * @param name
     * @param descriptor
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        return super.visitAnnotation(name, descriptor);
    }

    /**
     * 访问注解的数组值
     * @param name
     * @return
     */
    @Override
    public AnnotationVisitor visitArray(String name) {
        return super.visitArray(name);
    }

    /**
     * 访问结束通知
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
