package com.example.socialnetworkchatfx.validators;

import com.example.socialnetworkchatfx.domain.User;

public class UserValidator implements Validator<User>{

    @Override
    public void validate(User entity) throws ValidationException {
        String message="";
        if(entity.getFirstName().isEmpty())
            message+="First name can't be empty! ";
        if(entity.getLastName().isEmpty())
            message+="Last name can't be empty! ";
        if(entity.getEmail().isEmpty())
            message+="Email can't be empty! ";
        if(entity.getPassword().isEmpty())
            message+="Password can't be empty!";
    }
}
