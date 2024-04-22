package com.moozi.mooziweb.cart.exception;

public class ShoppingCartUpdateException extends RuntimeException{
    public ShoppingCartUpdateException() {
        super();
    }

    public ShoppingCartUpdateException(String info) {
        super(info);
    }
}
