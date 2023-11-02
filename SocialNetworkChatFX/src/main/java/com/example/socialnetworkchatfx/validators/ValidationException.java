package com.example.socialnetworkchatfx.validators;

public class ValidationException extends RuntimeException{

    public ValidationException(){

    }

    public ValidationException(String message){
        super(message);
    }
}
