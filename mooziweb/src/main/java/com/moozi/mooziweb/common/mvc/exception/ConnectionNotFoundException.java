package com.moozi.mooziweb.common.mvc.exception;

public class ConnectionNotFoundException extends RuntimeException{
    public ConnectionNotFoundException(){
        super();
    }

    public ConnectionNotFoundException(String message) {
        super(message);
    }
}
