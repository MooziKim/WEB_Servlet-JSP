package com.moozi.mooziweb.user.exception;

public class PointInfoSaveException extends RuntimeException{
    public PointInfoSaveException() {
        super();
    }

    public PointInfoSaveException(String pointInfoId) {
        super(pointInfoId);
    }
}
