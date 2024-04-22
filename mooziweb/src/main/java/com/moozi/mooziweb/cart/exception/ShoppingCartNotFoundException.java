package com.moozi.mooziweb.cart.exception;

public class ShoppingCartNotFoundException extends RuntimeException{
    public ShoppingCartNotFoundException() {
        super();
    }

    public ShoppingCartNotFoundException(String info) {
        super(info);
    }
}
