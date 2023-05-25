package com.hnust.zsg.Exception;

public class ServiceLimitException extends RuntimeException {
    public ServiceLimitException() {
    }

    public ServiceLimitException(String info) {
        super(info);
    }
}
