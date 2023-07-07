package com.ian.exception;

/**
 * 自定義業務異常
 */
public class CustomerException extends  RuntimeException {
    public CustomerException(String msg) {
        super(msg);
    }
}
