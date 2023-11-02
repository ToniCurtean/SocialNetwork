package com.example.socialnetworkchatfx.repository;

import com.example.socialnetworkchatfx.domain.Message;

import java.util.List;

public interface MessageRepository extends Repository<Integer, Message>{

    public List<Message> getMessagesForUsers(Integer id1, Integer id2);
}
