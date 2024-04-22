package com.moozi.mooziweb.user.exception;

public class UserDeleteException extends RuntimeException{
    public UserDeleteException(){
        super();
    }

    public UserDeleteException(String userId) {
        super(userId);
    }
}
