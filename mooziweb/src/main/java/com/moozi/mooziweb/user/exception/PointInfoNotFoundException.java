package com.moozi.mooziweb.user.exception;

public class PointInfoNotFoundException extends RuntimeException{
    public PointInfoNotFoundException() {
        super();
    }

    public PointInfoNotFoundException(String pointInfoId) {
        super(pointInfoId);
    }
}
