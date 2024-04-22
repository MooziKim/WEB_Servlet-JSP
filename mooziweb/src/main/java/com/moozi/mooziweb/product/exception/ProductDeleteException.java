package com.moozi.mooziweb.product.exception;
public class ProductDeleteException extends RuntimeException{
    public ProductDeleteException() {
        super();
    }

    public ProductDeleteException(String productInfo) {
        super(productInfo);
    }
}
