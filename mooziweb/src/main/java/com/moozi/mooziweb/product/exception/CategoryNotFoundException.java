package com.moozi.mooziweb.product.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String categoryInfo) {
        super(categoryInfo);
    }
}
