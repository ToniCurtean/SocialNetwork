package com.example.socialnetworkchatfx;

import com.example.socialnetworkchatfx.controller.LoginController;
import com.example.socialnetworkchatfx.domain.User;
import com.example.socialnetworkchatfx.repository.MessageRepository;
import com.example.socialnetworkchatfx.repository.db.FriendshipDBRepository;
import com.example.socialnetworkchatfx.repository.db.JdbcUtils;
import com.example.socialnetworkchatfx.repository.db.MessageDBRepository;
import com.example.socialnetworkchatfx.repository.db.UserDBRepository;
import com.example.socialnetworkchatfx.service.Service;
import com.example.socialnetworkchatfx.service.ServiceFriendship;
import com.example.socialnetworkchatfx.service.ServiceMessage;
import com.example.socialnetworkchatfx.service.ServiceUser;
import com.example.socialnetworkchatfx.validators.UserValidator;
import com.example.socialnetworkchatfx.validators.Validator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

public class Main extends Application {

    JdbcUtils jdbcUtils;
    UserDBRepository userDBRepository;
    MessageDBRepository messageDBRepository;
    FriendshipDBRepository friendshipDBRepository;

    ServiceUser serviceUser;
    ServiceMessage serviceMessage;
    ServiceFriendship serviceFriendship;

    Service service;
    UserValidator userValidator;



    public static void main(String[] args) {
        launch();



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(LocalDateTime.now());
        Properties properties=new Properties();
        try{
            properties.load(new FileReader("bd.config"));
        }catch(IOException e){
            System.out.println("Cannot find bd.config"+e);
        }
        JdbcUtils.setJdbcProps(properties);
        userDBRepository=new UserDBRepository();
        messageDBRepository=new MessageDBRepository();
        friendshipDBRepository=new FriendshipDBRepository();

        userValidator=new UserValidator();

        serviceUser=new ServiceUser(userDBRepository,userValidator);
        serviceMessage=new ServiceMessage(messageDBRepository);
        serviceFriendship=new ServiceFriendship(friendshipDBRepository);

        service=new Service(serviceUser,serviceFriendship,serviceMessage);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 360);
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginController loginController=fxmlLoader.getController();
        loginController.init();
        loginController.setService(service);

    }
}
