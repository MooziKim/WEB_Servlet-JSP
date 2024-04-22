package com.moozi.mooziweb.user.exception;

public class UserAddressDeleteException extends RuntimeException{
    public UserAddressDeleteException() {
        super();
    }

    public UserAddressDeleteException(String addressId) {
        super(addressId);
    }
}
