package com.hnust.zsg.Excption.validation;

public class EmailillegalException extends RuntimeException{
    public EmailillegalException(){}
    public EmailillegalException(String str){
        super(str);
    }
}
