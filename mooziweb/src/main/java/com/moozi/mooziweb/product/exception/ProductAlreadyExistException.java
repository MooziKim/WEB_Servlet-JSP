package com.moozi.mooziweb.product.exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException() {
        super();
    }

    public ProductAlreadyExistException(String productInfo) {
        super(productInfo);
    }
}
