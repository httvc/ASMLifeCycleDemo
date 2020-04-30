package com.httvc.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

/**
 * 按如下顺序调用
 * visit,visitEnd必须调用一次
 * visitSource,visitModule,visitNestHost,visitOuterClass 这些方法最多调用一次
 * visitAnnotation,visitTypeAnnotation,visitAttribute 这三个访问时按照顺序可以调用多次
 * visitNestMember,visitInnerClass,visitField,visitMethod 这三个访问时按照顺序可以调用多次
 */
public class LifeCycleClassVisitor1 extends ClassVisitor {

    public LifeCycleClassVisitor1(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    /**
     * 访问类的头部
     * @param version     类的版本
     * @param access      类的修饰符
     * @param name        类的名称
     * @param signature   类的签名，类不是泛型或者没有继承泛型列，singature为空
     * @param superName   类的父类名称
     * @param interfaces
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * 访问类的源码，就是.java文件，一般情况用不上
     * @param source
     * @param debug
     */
    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }

    /**
     * 用的比较少
     * @param name
     * @param access
     * @param version
     * @return
     */
    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        return super.visitModule(name, access, version);
    }

    /**
     * 访问类的 nest host
     * @param nestHost
     */
    @Override
    public void visitNestHost(String nestHost) {
        super.visitNestHost(nestHost);
    }

    /**
     * 访问类的外部类 一般用于nest-class
     * @param owner
     * @param name
     * @param descriptor
     */
    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        super.visitOuterClass(owner, name, descriptor);
    }

    /**
     * 访问类的注解
     * @param descriptor 表示类注解类的描述
     * @param visible    表示该注解是否运行时可见
     * @return AnnotationVisitor 表示该注解类的visitor，可以用来访问注解值
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }


    /**
     * 访问类的签名类型(某个泛型)的注解
     * @param typeRef 指的是类型引用，这里只能是TypeReference.(CLASS_TYPE_PARAMETER|CLASS_TYPE_PARAMETER_BOUND?CLASS_EXTENDS)
     * @param typePath 被注解的类型参数，
     * @param descriptor 注解类的描述
     * @param visible 该注解类型运行时是否可见
     * @return
     */
    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    /**
     * 访问类的非标准属性
     * @param attribute
     */
    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    /**
     * 访问嵌套类的nest member，只有host class 被visited时才能调用该方法
     * @param nestMember
     */
    @Override
    public void visitNestMember(String nestMember) {
        super.visitNestMember(nestMember);
    }

    /**
     * 访问一个内部内的信息
     * @param name
     * @param outerName
     * @param innerName
     * @param access
     */
    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    /**
     * 访问一个类的域信息，如果需要修改或者新增一个域，可以通过重写此方法
     * @param access      表示该域的访问方式，public，private或者static，final等
     * @param name        该域的名称
     * @param descriptor  域的描述，一般指该field的参数类型
     * @param signature   域的签名，一般泛型才会有签名
     * @param value       指该域的初始值
     * @return FieldVisitor 返回一个可以访问该域注解和属性的访问对象，可以设置为空
     */
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(access, name, descriptor, signature, value);
    }

    /**
     * 访问类的方法 需要修改该类的方法信息，则可以重写此方法
     * @param access
     * @param name
     * @param descriptor 表方法的参数类型和返回值类型
     * @param signature
     * @param exceptions
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    /**
     * 访问类的尾部，只有当类访问结束时，才能调用该方法，同时必须调用该方法
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
