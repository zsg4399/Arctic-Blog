package com.hnust.zsg.Excption.validation;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountDisabledException extends UsernameNotFoundException {
    public AccountDisabledException(String msg) {
        super(msg);
    }
}
