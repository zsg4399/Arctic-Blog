package com.hnust.zsg.Excption.validation;

public class NoSuchFieldException extends RuntimeException{
    public NoSuchFieldException(String message){
        super(message);
    }
}
