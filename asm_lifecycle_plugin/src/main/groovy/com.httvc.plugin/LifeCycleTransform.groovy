package com.httvc.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.httvc.asm.LifecycleClassVisitor
import groovy.io.FileType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

/**
 * Transform 在.class文件转换成.dex的流程中会执行这些task，
 * 对所有的.class文件进行转换
 * Transform 主要作用是检索项目编译过程中的所有文件
 */
public class LifeCycleTransform extends Transform {

    /**
     * 设置我们自定义的Transform对应的Task名称。
     * Gradle在编译的时候，会将这个名称显示在控制台上
     * 比如：Task:app:transformClassesWithXXXForDebug
     * @return
     */
    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    /**
     * 在项目中会有各种各样格式的文件
     * 通过getInputType可以设置LifeCycleTransform接收文件类型
     * 此方法返回的类型是Set<QualifiedContent.ContentType>集合
     * ContentType有2中取值
     * 1.CLASSES：代表只检索.class文件
     * 2.RESOURCES:代表检索java标准资源文件
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 这个方法规定自定义Transform检索的范围，具体有以下几种取值：
     * EXTERNAL_LIBRARIES   只有外部库
     * PROJECT              只有项目内容
     * PROJECT_LOCAL_DEPS   只有项目本地依赖(本地jar)
     * PROVIDED_ONLY        只提供本地或远程依赖项
     * SUB_PROJECTS         只有子项目
     * SUB_PROJECTS_LOCAL_DEPS 只有子项目的本地依赖项(本地jar)
     * TESTED_CODE             由当前变量(包括依赖项)测试的代码
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    /**
     * 表示当前Transform是否支持增量编译，
     * 不需要增量编译，直接返回false即可
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * @param inputs inputs中是传过来的输入流，其中两种格式，一种是jar包格式，一种是directory(目录格式)
     * @param outputProvider outputProvider获取到输出目录，最后将修改的文件复制到输出目录，这一步必须做，不然编译报错
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException,
            InterruptedException, IOException {

         //第一种写法
        //拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs

        TransformOutputProvider outputProvider=transformInvocation.outputProvider

        transformInputs.each { TransformInput transformInput ->
            //directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            //比如我们手写的类以及R.class,BuildConfig.class以及MainActivity.class
           /* transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                File dir = directoryInput.file
                if (dir) {
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        System.out.println("find class:" + file.name)
                        //对class文件进行读取与解析
                        ClassReader classReader=new ClassReader(file.bytes)
                        //对class文件的写入
                        ClassWriter classWriter=new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
                        //访问class文件相应的内容，解析到某一个结构就会通知到ClassVisitor的相应方法
                        ClassVisitor classVisitor=new LifecycleClassVisitor(classWriter)
                        //依次调用ClassVisitor接口的各个方法
                        classReader.accept(classVisitor,ClassReader.SKIP_FRAMES)
                        //toByteArray方法会将最终修改的字节码以byte数组形式返回
                        byte[] bytes=classWriter.toByteArray()
                        //通过文件流写入方式覆盖掉原先的内容，实现class文件的改写
                        FileOutputStream outputStream=new FileOutputStream(file.path)
                        outputStream.write(bytes)
                        outputStream.close()
                    }
                }
                //处理完输入文件后把输出传给下一个文件
                def dest=outputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file,dest)
            }*/

            //Gradle3.6版本之后，R文件只会单独编译成R.jar
            //所以要把R.jar单独复制过去
            // Gradle3.6版本以前不需要此段代码
           /* transformInput.jarInputs.each {JarInput jarInput->
                File file=jarInput.file
                System.out.println("find jar input:"+file.name)
                def dest=outputProvider.getContentLocation(jarInput.name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                FileUtils.copyFile(file,dest)
            }*/
        }

        transformInputs.each {TransformInput transformInput ->
            //directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            //比如我们手写的类以及R.class,BuildConfig.class以及MainActivity.class，
            // Gradle3.6以后R文件打包成R.jar文件了
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                    handleDirectoryInput(directoryInput)
                    //处理完输入文件后把输出传给下一个文件
                    def dest=outputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file,dest)
            }
            transformInput.jarInputs.each { JarInput jarInput->
                   handleJarInput(jarInput)
                   //处理完输入文件后把输出传给下一个文件
                   def dest=outputProvider.getContentLocation(jarInput.name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                   FileUtils.copyFile(jarInput.file,dest)
            }
        }
    }

    void handleDirectoryInput(DirectoryInput directoryInput){
        File dir = directoryInput.file
        if (dir) {
            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                System.out.println("find class:" + file.name)
                //对class文件进行读取与解析
                ClassReader classReader=new ClassReader(file.bytes)
                //对class文件的写入
                ClassWriter classWriter=new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
                //访问class文件相应的内容，解析到某一个结构就会通知到ClassVisitor的相应方法
                ClassVisitor classVisitor=new LifecycleClassVisitor(classWriter)
                //依次调用ClassVisitor接口的各个方法
                classReader.accept(classVisitor,ClassReader.SKIP_FRAMES)
                //toByteArray方法会将最终修改的字节码以byte数组形式返回
                byte[] bytes=classWriter.toByteArray()
                //通过文件流写入方式覆盖掉原先的内容，实现class文件的改写
                FileOutputStream outputStream=new FileOutputStream(file.path)
                outputStream.write(bytes)
                outputStream.close()
            }
        }
    }

    void handleJarInput(JarInput jarInput){
        File file=jarInput.file
        System.out.println("find jar input:"+file.name)
    }
}