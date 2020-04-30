package com.httvc.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

/**
 * FieldVisitor是用来在访问类的域字节码过程中创建域或者修改域字节码信息的
 * 按如下顺序访问
 * visitAnnotation,visitTypeAnnotation,visitAttribute 可以多次访问
 * visitEnd 访问结束时必须访问一次
 */
public class LifecycleFieldVisitor1 extends FieldVisitor {

    public LifecycleFieldVisitor1(int api, FieldVisitor fieldVisitor) {
        super(api, fieldVisitor);
    }

    /**
     * 访问域的注解
     * @param descriptor 表示注解类的描述，即注解类的描述：如Ljava/lang/JavaBean等
     * @param visible 表示该注解运行时是否可见
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

    /**
     * 访问域类型上的注解
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
     * 访问域属性
     * @param attribute
     */
    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    /**
     * 域访问完成后必须调用
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
