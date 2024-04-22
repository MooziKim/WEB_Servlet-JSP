package com.moozi.mooziweb.review.exception;

public class ReviewDeleteException extends RuntimeException{
    public ReviewDeleteException() {
        super();
    }

    public ReviewDeleteException(String info) {
        super(info);
    }
}
