package com.moozi.mooziweb.review.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException() {
        super();
    }

    public ReviewNotFoundException(String info) {
        super(info);
    }
}
