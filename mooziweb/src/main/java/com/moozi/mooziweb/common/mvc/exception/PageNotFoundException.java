package com.moozi.mooziweb.common.mvc.exception;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(String path){
        super(String.format("page not found:%s",path));
    }
}