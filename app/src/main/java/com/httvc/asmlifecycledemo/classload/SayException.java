package com.httvc.asmlifecycledemo.classload;

public class SayException implements ISay {
    @Override
    public String saySomething() {
        return "Something wrong here!";
    }
}
