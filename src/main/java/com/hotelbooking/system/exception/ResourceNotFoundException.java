package com.hotelbooking.system.exception;


public class ResourceNotFoundException  extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
