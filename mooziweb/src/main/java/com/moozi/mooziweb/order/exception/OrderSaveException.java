package com.moozi.mooziweb.order.exception;

public class OrderSaveException extends RuntimeException{
    public OrderSaveException() {
        super();
    }

    public OrderSaveException(String info) {
        super(info);
    }
}
