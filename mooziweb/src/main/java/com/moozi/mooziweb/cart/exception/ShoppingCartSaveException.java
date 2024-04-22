package com.moozi.mooziweb.cart.exception;

public class ShoppingCartSaveException extends RuntimeException{
    public ShoppingCartSaveException() {
        super();
    }

    public ShoppingCartSaveException(String info) {
        super(info);
    }
}
