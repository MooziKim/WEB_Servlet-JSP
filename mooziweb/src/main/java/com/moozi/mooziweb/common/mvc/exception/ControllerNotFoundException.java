package com.moozi.mooziweb.common.mvc.exception;

public class ControllerNotFoundException extends RuntimeException{
    public ControllerNotFoundException(String key){
        super(String.format("controller not found:%s",key));
    }
}
