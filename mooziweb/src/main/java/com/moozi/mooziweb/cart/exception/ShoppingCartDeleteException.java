package com.moozi.mooziweb.cart.exception;

public class ShoppingCartDeleteException extends RuntimeException{
    public ShoppingCartDeleteException() {
        super();
    }

    public ShoppingCartDeleteException(String info) {
        super(info);
    }
}
