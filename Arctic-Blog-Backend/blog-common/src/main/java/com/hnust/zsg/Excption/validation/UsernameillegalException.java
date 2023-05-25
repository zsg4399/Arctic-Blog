package com.hnust.zsg.Excption.validation;

import org.springframework.security.core.AuthenticationException;

public class UsernameillegalException extends AuthenticationException {

    public UsernameillegalException(String str){
        super(str);
    }
}
