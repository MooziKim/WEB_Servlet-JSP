package com.moozi.mooziweb.order.exception;

public class OrderAlreadyExistException extends RuntimeException{
    public OrderAlreadyExistException() {
        super();
    }

    public OrderAlreadyExistException(String info) {
        super(info);
    }
}
