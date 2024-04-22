package com.moozi.mooziweb.user.exception;

public class AddressCheckException extends RuntimeException{
    public AddressCheckException() {
        super();
    }

    public AddressCheckException(String info) {
        super(info);
    }
}
