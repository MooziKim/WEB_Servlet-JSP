package com.moozi.mooziweb.review.exception;

public class ReviewAlreadyExistException extends RuntimeException{
    public ReviewAlreadyExistException() {
        super();
    }

    public ReviewAlreadyExistException(String info) {
        super(info);
    }
}
