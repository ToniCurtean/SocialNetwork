package com.example.socialnetworkchatfx.controller;

import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;
import com.example.socialnetworkchatfx.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    private Service service;

    @FXML
    private Label firstName;

    @FXML
    private TextField firstNameText;

    @FXML
    private Label lastName;

    @FXML
    private TextField lastNameText;

    @FXML
    private Label email;

    @FXML
    private TextField emailText;

    @FXML
    private Label password;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button registerButton;

    public void setService(Service service) {
        this.service=service;
    }

    public void init(){
        firstNameText.setOnKeyPressed(event->{
            if(event.getCode()== KeyCode.ENTER)
                lastNameText.requestFocus();
        });
        lastNameText.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                emailText.requestFocus();
        });
        emailText.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                passwordText.requestFocus();
        });
        passwordText.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                try{
                    onRegisterButtonClick(new ActionEvent());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }catch(RepositoryException e){

                }
        });
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException, RepositoryException{
        try{
            service.getServiceUser().addUser(firstNameText.getText(),lastNameText.getText(), emailText.getText(),passwordText.getText());
            ((Stage) registerButton.getScene().getWindow()).close();
        }catch(RepositoryException e){
            UIAlert.showMessage(null, Alert.AlertType.ERROR,"Can't register!",e.getMessage());
        }
    }



}
