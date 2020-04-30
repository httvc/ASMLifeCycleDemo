package com.httvc.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

//实现Plugin<Project> 接口原因是相当于入口，当app module的build.gradle文件中使用此插件时，
//LifeCyclePlugin的Apply方法会被自动调用
public class LifeCyclePlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        System.out.println("==LifeCyclePlugin gradle plugin==")

        def android=project.extensions.getByType(AppExtension)
        println'------------registering AutoTrackTransform ------------------'
        LifeCycleTransform transform=new LifeCycleTransform()
        android.registerTransform(transform)
    }
}