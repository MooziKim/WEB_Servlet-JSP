package com.moozi.mooziweb.product.exception;

public class CategoriesForProductsAlreadyExistException extends RuntimeException{
    public CategoriesForProductsAlreadyExistException() {
        super();
    }

    public CategoriesForProductsAlreadyExistException(String info) {
        super(info);
    }
}
