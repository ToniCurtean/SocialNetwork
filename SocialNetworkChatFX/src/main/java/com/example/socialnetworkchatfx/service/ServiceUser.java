package com.example.socialnetworkchatfx.service;

import com.example.socialnetworkchatfx.domain.User;
import com.example.socialnetworkchatfx.repository.UserRepository;
import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;
import com.example.socialnetworkchatfx.validators.UserValidator;
import com.example.socialnetworkchatfx.validators.ValidationException;
import com.example.socialnetworkchatfx.validators.Validator;

public class ServiceUser {

    private UserRepository userRepository;

    private UserValidator validator;

    public ServiceUser(UserRepository userRepository,UserValidator userValidator) {
        this.userRepository = userRepository;
        this.validator=userValidator;
    }

    public void addUser(String firstName,String lastName,String email,String password){
        User user=new User(firstName,lastName,email,password);
        try{
            this.validator.validate(user);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        try{
            this.userRepository.save(user);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUserByEmailPassword(String email,String password){
        User user=null;
        try{
            user=this.userRepository.findByEmailPassword(email,password);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User getUserByName(String firstName,String lastName){
        User user=null;
        try{
            user=this.userRepository.findByName(firstName,lastName);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }


    public User getUserById(Integer id) {
        User user=null;
        try{
            user=this.userRepository.findOne(id);
        }catch (RepositoryException e){
            System.out.println(e.getMessage());
        }
        return user;
    }
}
