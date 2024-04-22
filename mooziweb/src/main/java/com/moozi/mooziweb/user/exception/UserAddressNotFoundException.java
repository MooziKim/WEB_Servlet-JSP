package com.moozi.mooziweb.user.exception;

public class UserAddressNotFoundException extends RuntimeException{
    public UserAddressNotFoundException(){
        super();
    }

    public UserAddressNotFoundException(String userId){
        super(userId);
    }
}
