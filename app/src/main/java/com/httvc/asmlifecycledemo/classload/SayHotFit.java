package com.httvc.asmlifecycledemo.classload;

public class SayHotFit implements ISay {
    @Override
    public String saySomething() {
        return "EveryThing is Right";
    }
}

class Parent{
    private int age;
    Parent(int age){
        System.out.println("parent:"+age);
        this.age=this.age;
    }
}

class Child extends Parent{
    Child(int age){
        super(age);
        System.out.println("child:"+age);
    }
    public static void main(String[] args){
        Child child=new Child(10);
    }
}


