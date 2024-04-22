package com.moozi.mooziweb.cart.exception;

public class ShoppingCartAlreadyExistException extends RuntimeException{
    public ShoppingCartAlreadyExistException() {
        super();
    }

    public ShoppingCartAlreadyExistException(String info) {
        super(info);
    }
}
