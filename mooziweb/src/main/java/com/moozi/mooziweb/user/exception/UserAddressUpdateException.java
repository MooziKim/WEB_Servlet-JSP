package com.moozi.mooziweb.user.exception;

public class UserAddressUpdateException extends RuntimeException{
    public UserAddressUpdateException() {
        super();
    }

    public UserAddressUpdateException(String userId) {
        super(userId);
    }
}
