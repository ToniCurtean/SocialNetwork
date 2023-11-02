package com.example.socialnetworkchatfx.controller;

import com.example.socialnetworkchatfx.domain.Message;
import com.example.socialnetworkchatfx.domain.User;
import com.example.socialnetworkchatfx.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ChatController {

    private Service service;
    private User loggedUser;
    private User otherUser;

    @FXML
    private Label otherUserName;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messagesView;

    private final ObservableList<String> messages = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    public void init(){
        otherUserName.setText("You are chating to "+otherUser.getFirstName()+" "+otherUser.getLastName());
        messagesView.setItems(messages);
        loadMessages();
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                onSendMessageButtonClick(new ActionEvent());
        });
    }

    private void loadMessages(){
        messages.clear();
        for(Message m:service.getServiceMessage().getMessages(loggedUser.getId(),otherUser.getId())){
            if(m.getIdUser1().equals(loggedUser.getId())){
                messages.add(loggedUser.getLastName()+":"+m.getText());
                continue;
            }
            if(m.getIdUser1().equals(otherUser.getId())){
                messages.add(otherUser.getLastName()+":"+m.getText());
            }
        }
    }

    @FXML
    public void onSendMessageButtonClick(ActionEvent e){
        if(messageField.getText().strip().equals(""))
            return;
        String text= messageField.getText();
        service.getServiceMessage().addMessage(loggedUser.getId(),otherUser.getId(),text);
        loadMessages();
        messageField.setText("");
    }

}
