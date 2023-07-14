package com.hnust.zsg.Excption.validation;

public class ArticleInfoException extends RuntimeException{
    public ArticleInfoException(){}
    public ArticleInfoException(String msg){
        super(msg);
    }
}
