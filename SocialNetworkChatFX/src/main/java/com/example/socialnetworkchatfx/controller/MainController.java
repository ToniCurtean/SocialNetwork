package com.example.socialnetworkchatfx.controller;

import com.example.socialnetworkchatfx.Main;
import com.example.socialnetworkchatfx.domain.Friendship;
import com.example.socialnetworkchatfx.domain.User;
import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;
import com.example.socialnetworkchatfx.service.Service;
import com.example.socialnetworkchatfx.validators.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Service service;

    private User loggedUser;

    @FXML
    private Label userName;

    @FXML
    private Button acceptRequestButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button withdrawRequestButton;
    @FXML
    private Button sendRequestButton;

    @FXML
    private Button declineRequestButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField newFriend;

    @FXML
    private ChoiceBox<String> friendsBox;

    @FXML
    private ListView<String> friendsView;

    @FXML
    private ListView<String> requestsView;

    private final ObservableList<String> friends = FXCollections.observableArrayList();

    private final ObservableList<String> requests = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;

    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void init() {
        userName.setText("Welcome," + this.loggedUser.getFirstName() + " " + this.loggedUser.getLastName());
        friendsView.setItems(friends);
        requestsView.setItems(requests);
        friendsBox.setItems(friends);
        loadFriends();
        loadRequests();
        newFriend.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                try {
                    onSendRequestButtonClick(new ActionEvent());
                } catch (RepositoryException e) {

                }
        });

    }

    public void loadFriends(){
        friends.clear();
        for(Friendship f:service.getServiceFriendship().getFriendships(loggedUser,"ACCEPTED")){
            String friendStr="";
            if(f.getIdUser1().equals(loggedUser.getId())){
                User friend=service.getServiceUser().getUserById(f.getIdUser2());
                friendStr=friend.getFirstName()+" "+friend.getLastName();
                friends.add(friendStr);
                continue;
            }
            if(f.getIdUser2().equals(loggedUser.getId())){
                User friend=service.getServiceUser().getUserById(f.getIdUser1());
                friendStr=friend.getFirstName()+" "+friend.getLastName();
                friends.add(friendStr);
            }
        }
    }

    private void loadRequests(){
        requests.clear();
        for(Friendship f:service.getServiceFriendship().getFriendships(loggedUser,"PENDING")){
            String friendStr="";
            if(f.getIdUser1().equals(loggedUser.getId())){
                User friend=service.getServiceUser().getUserById(f.getIdUser2());
                friendStr=friend.getFirstName()+" "+friend.getLastName();
                requests.add(friendStr);
                continue;
            }
            if(f.getIdUser2().equals(loggedUser.getId())){
                User friend=service.getServiceUser().getUserById(f.getIdUser1());
                friendStr=friend.getFirstName()+" "+friend.getLastName();
                requests.add(friendStr);
            }
        }
    }


    public void onSendRequestButtonClick(ActionEvent actionEvent) {
        if (newFriend.getText().strip().equals(""))
            return;
        try {
            String[] data = newFriend.getText().split(" ");
            if(data.length<2){
                UIAlert.showMessage(null, Alert.AlertType.INFORMATION,"","You should search by first name and last name!");
                return;
            }
            try{
                User requestUser = service.getServiceUser().getUserByName(data[0], data[1]);
                service.getServiceFriendship().addFriendship(loggedUser.getId(), requestUser.getId());
            }catch(ValidationException ex){
                UIAlert.showMessage(null, Alert.AlertType.INFORMATION,"","Can't send another friend request!");
                return;
            }
            catch (RepositoryException e){
                UIAlert.showMessage(null, Alert.AlertType.INFORMATION,"","No user with given name!");
                return;
            }
            loadRequests();
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Friend request sent successfully");
        } catch (RepositoryException e) {
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Couldn't send friend request!");
        }
        newFriend.setText("");
    }


    public void onAcceptButtonClick(ActionEvent actionEvent) {
        if (requestsView.getSelectionModel().getSelectedItem() == null)
            return;
        String selected = requestsView.getSelectionModel().getSelectedItem();
        String[] data = selected.split(" ");
        User friend = service.getServiceUser().getUserByName(data[0],data[1]);
        try{
            service.getServiceFriendship().acceptRequest(friend.getId(), getLoggedUser().getId());
            loadFriends();
            loadRequests();
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Friendship accepted successfully");
        }catch (RepositoryException e) {
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Couldn't accept friendship");
        }
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        if (friendsView.getSelectionModel().getSelectedItem() == null)
            return;
        String selected = friendsView.getSelectionModel().getSelectedItem();
        String[] data = selected.split(" ");
        User friend = service.getServiceUser().getUserByName(data[0],data[1]);
        try {
            service.getServiceFriendship().deleteFriendship(friend.getId(), loggedUser.getId());
            loadFriends();
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Friendship deleted successfully");
        } catch (RepositoryException e) {
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Could not delete friendship!");
        }
    }

    public void onDeclineButtonClick(ActionEvent actionEvent) {
        if (requestsView.getSelectionModel().getSelectedItem() == null)
            return;
        String selected = requestsView.getSelectionModel().getSelectedItem();
        String[] data = selected.split(" ");
        User requestUser = service.getServiceUser().getUserByName(data[0],data[1]);
        try{
            service.getServiceFriendship().declineRequest(requestUser.getId(), loggedUser.getId());
            loadRequests();
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Friendship declined successfully");
        }catch (RepositoryException e) {
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Couldn't decline friendship");
        }
    }

    public void onWithdrawButtonClick(ActionEvent actionEvent) {
        if(requestsView.getSelectionModel().getSelectedItem()==null)
            return;
        String selected=requestsView.getSelectionModel().getSelectedItem();
        String[] data=selected.split(" ");
        User requestUser=service.getServiceUser().getUserByName(data[0],data[1]);
        try{
            service.getServiceFriendship().withdrawRequest(loggedUser.getId(),requestUser.getId());
            loadRequests();
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Friendship withdrawn successfully");
        }catch (RepositoryException e) {
            UIAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Couldn't withdraw friendship");
        }

    }

    public void onSelectChoiceBox(MouseEvent event) throws IOException {
        if(friendsBox.getSelectionModel().getSelectedItem()==null)
            return;
        String selected=friendsBox.getSelectionModel().getSelectedItem();
        String[] data=selected.split(" ");
        User otherUser=service.getServiceUser().getUserByName(data[0],data[1]);
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 360);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle(data[0]+" "+data[1]);
        stage.show();
        ChatController chatController=loader.getController();
        chatController.setService(service);
        chatController.setLoggedUser(getLoggedUser());
        chatController.setOtherUser(otherUser);
        chatController.init();
    }



    public void onLogoutButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 360);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Welcome!");
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
        LoginController loginController = loader.getController();
        loginController.setService(service);
        loginController.init();
        ((Stage) logoutButton.getScene().getWindow()).close();
    }

}
