package com.moozi.mooziweb.review.exception;

public class ReviewUpdateException extends RuntimeException{
    public ReviewUpdateException() {
        super();
    }

    public ReviewUpdateException(String info) {
        super(info);
    }
}
