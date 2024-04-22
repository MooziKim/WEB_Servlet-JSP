package com.moozi.mooziweb.product.exception;

public class CategoriesForProductsNotFoundException extends RuntimeException{
    public CategoriesForProductsNotFoundException() {
        super();
    }

    public CategoriesForProductsNotFoundException(String info) {
        super(info);
    }
}
