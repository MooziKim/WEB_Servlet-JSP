package com.moozi.mooziweb.user.exception;

public class UserUpdateException extends RuntimeException{
    public UserUpdateException() {
        super();
    }

    public UserUpdateException(String userId) {
        super(userId);
    }
}
