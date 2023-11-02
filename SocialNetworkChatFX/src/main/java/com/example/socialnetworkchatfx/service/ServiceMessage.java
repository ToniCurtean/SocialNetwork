package com.example.socialnetworkchatfx.service;

import com.example.socialnetworkchatfx.domain.Message;
import com.example.socialnetworkchatfx.repository.MessageRepository;

import java.util.List;

public class ServiceMessage {
    private MessageRepository messageRepository;

    public ServiceMessage(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> getMessages(Integer id1, Integer id2) {
        return this.messageRepository.getMessagesForUsers(id1,id2);
    }

    public void addMessage(Integer id1, Integer id2, String text) {
        Message message=new Message(id1,id2,text);
        this.messageRepository.save(message);
    }
}
