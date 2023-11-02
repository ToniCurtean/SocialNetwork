package com.example.socialnetworkchatfx.repository;

import com.example.socialnetworkchatfx.domain.Friendship;
import com.example.socialnetworkchatfx.domain.FriendshipStatus;

public interface FriendshipRepository extends Repository<Integer, Friendship>{

    public void updateFriendshipForUsers(Integer id1, Integer id2, FriendshipStatus status);

    public void deleteFriendshipForUsers(Integer id1,Integer id2);

}
