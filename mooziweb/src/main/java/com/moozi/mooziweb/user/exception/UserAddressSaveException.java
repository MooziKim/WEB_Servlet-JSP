package com.moozi.mooziweb.user.exception;

public class UserAddressSaveException extends RuntimeException{
    public UserAddressSaveException() {
        super();
    }

    public UserAddressSaveException(String userId) {
        super(userId);
    }
}
