package com.moozi.mooziweb.product.exception;

public class ProductSaveException extends RuntimeException{
    public ProductSaveException() {
        super();
    }

    public ProductSaveException(String productInfo) {
        super(productInfo);
    }
}
