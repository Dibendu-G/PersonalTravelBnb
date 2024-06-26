package com.travelbnb.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String Message){
        super(Message);
    }
}
