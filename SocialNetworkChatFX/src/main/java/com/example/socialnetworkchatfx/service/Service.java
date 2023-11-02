package com.example.socialnetworkchatfx.service;

public class Service {
    private final ServiceUser serviceUser;
    private final ServiceFriendship serviceFriendship;
    private final ServiceMessage serviceMessage;

    public Service(ServiceUser serviceUser, ServiceFriendship friendship, ServiceMessage serviceMessage) {
        this.serviceUser = serviceUser;
        this.serviceFriendship = friendship;
        this.serviceMessage = serviceMessage;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public ServiceFriendship getServiceFriendship() {
        return serviceFriendship;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }
}
