package com.example.socialnetworkchatfx.repository;


import com.example.socialnetworkchatfx.domain.User;

public interface UserRepository extends Repository<Integer, User>{
    User findByEmailPassword(String email,String password);
    User findByName(String firstName,String lastName);
}
