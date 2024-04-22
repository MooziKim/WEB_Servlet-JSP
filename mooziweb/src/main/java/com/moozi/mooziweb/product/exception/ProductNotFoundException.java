package com.moozi.mooziweb.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String productInfo) {
        super(productInfo);
    }
}
