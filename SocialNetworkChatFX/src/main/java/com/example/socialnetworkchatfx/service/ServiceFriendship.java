package com.example.socialnetworkchatfx.service;

import com.example.socialnetworkchatfx.domain.Friendship;
import com.example.socialnetworkchatfx.domain.FriendshipStatus;
import com.example.socialnetworkchatfx.domain.User;
import com.example.socialnetworkchatfx.repository.FriendshipRepository;
import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;
import com.example.socialnetworkchatfx.validators.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceFriendship {

    private FriendshipRepository friendshipRepository;

    public ServiceFriendship(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }


    public List<Friendship> getFriendships(User loggedUser,String status) {
        List<Friendship> friendships=new ArrayList<>();
        for(Friendship f: friendshipRepository.findAll()){
            if(Objects.equals(f.getFriendshipStatus().toString(), status) && (Objects.equals(f.getIdUser1(), loggedUser.getId()) || Objects.equals(f.getIdUser2(), loggedUser.getId()))){
                friendships.add(f);
            }
        }
        return friendships;
    }

    public void addFriendship(Integer id1, Integer id2) {
        System.out.println(id1+" "+id2);
        for(Friendship f: friendshipRepository.findAll()){
            if((f.getIdUser1().equals(id1) && f.getIdUser2().equals(id2)) || (f.getIdUser1().equals(id2) && f.getIdUser2().equals(id1))){
                if(f.getFriendshipStatus()==FriendshipStatus.ACCEPTED || f.getFriendshipStatus()==FriendshipStatus.PENDING){
                    throw new ValidationException("Can't send another friend request!");
                }
            }
        }
        Friendship friendship=new Friendship(id1,id2, FriendshipStatus.PENDING);
        this.friendshipRepository.save(friendship);
    }

    public void acceptRequest(Integer id1, Integer id2) {
        this.friendshipRepository.updateFriendshipForUsers(id1,id2,FriendshipStatus.ACCEPTED);
    }

    public void deleteFriendship(Integer id1, Integer id2) {
        this.friendshipRepository.deleteFriendshipForUsers(id1,id2);
    }

    public void declineRequest(Integer id1, Integer id2) {
        this.friendshipRepository.updateFriendshipForUsers(id1,id2,FriendshipStatus.DECLINED);
    }


    public void withdrawRequest(Integer id1, Integer id2) {
        this.friendshipRepository.updateFriendshipForUsers(id1,id2,FriendshipStatus.WITHDRAWN);
    }
}
