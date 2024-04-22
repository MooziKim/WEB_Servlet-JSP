package com.moozi.mooziweb.user.exception;

public class UserSaveException extends RuntimeException{
    public UserSaveException() {
        super();
    }

    public UserSaveException(String userId) {
        super(userId);
    }
}
