package com.moozi.mooziweb.product.exception;

public class ProductUpdateException extends RuntimeException{
    public ProductUpdateException() {
        super();
    }

    public ProductUpdateException(String productInfo) {
        super(productInfo);
    }
}
