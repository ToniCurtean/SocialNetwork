package com.example.socialnetworkchatfx.validators;

public interface Validator<T>{
    void validate(T entity) throws ValidationException;
}
