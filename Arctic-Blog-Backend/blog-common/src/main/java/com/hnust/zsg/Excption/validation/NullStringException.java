package com.hnust.zsg.Excption.validation;

public class NullStringException extends RuntimeException{
    public NullStringException(){}
    public NullStringException(String str){
        super(str);
    }
}
