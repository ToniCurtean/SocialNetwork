package com.example.socialnetworkchatfx.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Integer>{

    private Integer idUser1;
    private Integer idUser2;

    private LocalDateTime friendsFrom;

    private FriendshipStatus friendshipStatus;

    public Friendship(Integer idUser1, Integer idUser2,FriendshipStatus friendshipStatus) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.friendsFrom = LocalDateTime.now();
        this.friendshipStatus = friendshipStatus;
    }

    public Friendship(Integer id,Integer idUser1, Integer idUser2, LocalDateTime friendsFrom, FriendshipStatus friendshipStatus) {
        super.setId(id);
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.friendsFrom = friendsFrom;
        this.friendshipStatus = friendshipStatus;
    }

    public Integer getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Integer idUser1) {
        this.idUser1 = idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Integer idUser2) {
        this.idUser2 = idUser2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return idUser1.equals(that.idUser1) && idUser2.equals(that.idUser2) && friendsFrom.equals(that.friendsFrom) && friendshipStatus == that.friendshipStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser1, idUser2, friendsFrom, friendshipStatus);
    }
}
