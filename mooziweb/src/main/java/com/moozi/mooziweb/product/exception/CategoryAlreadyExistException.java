package com.moozi.mooziweb.product.exception;

public class CategoryAlreadyExistException extends RuntimeException{
    public CategoryAlreadyExistException() {
        super();
    }

    public CategoryAlreadyExistException(String categoryName) {
        super(categoryName);
    }
}
