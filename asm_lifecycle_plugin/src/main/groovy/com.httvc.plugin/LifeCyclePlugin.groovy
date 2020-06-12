package com.httvc.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

//实现Plugin<Project> 接口原因是相当于入口，当app module的build.gradle文件中使用此插件时，
//LifeCyclePlugin的Apply方法会被自动调用
public class LifeCyclePlugin implements Plugin<Project>{

    /**
     * 插件引入时要执行的方法
     * @param project 引入当前插件的Project
     */
    @Override
    void apply(Project project) {
        println "==LifeCyclePlugin gradle plugin=="+project.name

        def android=project.extensions.getByType(AppExtension)
        println'------------registering AutoTrackTransform ------------------'
        LifeCycleTransform transform=new LifeCycleTransform()
        android.registerTransform(transform)
        //创建扩展属性
        project.extensions.create('httvcReleaseInfo', ReleaseInfoExtersion)
        //创建Task
        project.tasks.create('httvcReleaseInfoTask',ReleaseInfoTask)
        def str="groovy"
        str.center(8,"a")
    }
}