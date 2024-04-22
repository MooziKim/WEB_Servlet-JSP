package com.moozi.mooziweb.order.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String info) {
        super(info);
    }
}
