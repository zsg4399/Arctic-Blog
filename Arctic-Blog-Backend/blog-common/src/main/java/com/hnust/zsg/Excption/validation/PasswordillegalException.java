package com.hnust.zsg.Excption.validation;

import org.springframework.security.core.AuthenticationException;

public class PasswordillegalException extends AuthenticationException {
    public PasswordillegalException(String str){
        super(str);
    }
}
