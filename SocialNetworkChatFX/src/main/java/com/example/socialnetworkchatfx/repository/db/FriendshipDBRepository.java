package com.example.socialnetworkchatfx.repository.db;

import com.example.socialnetworkchatfx.domain.Friendship;
import com.example.socialnetworkchatfx.domain.FriendshipStatus;
import com.example.socialnetworkchatfx.repository.FriendshipRepository;
import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FriendshipDBRepository implements FriendshipRepository {

    private JdbcUtils dbUtils;

    public FriendshipDBRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Friendship findOne(Integer integer) {

        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendshipsList=new ArrayList<>();
        Connection conn=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=conn.prepareStatement("select * from friendship")){
            try(ResultSet res=preparedStatement.executeQuery()){
                while(res.next()){
                    Friendship friendship=new Friendship(res.getInt("id"),res.getInt("idUser1"),res.getInt("idUser2"), LocalDateTime.parse(res.getString("friendsFrom")), FriendshipStatus.valueOf(res.getString("status")));
                    friendshipsList.add(friendship);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(friendshipsList.isEmpty())
            throw new RepositoryException("No friendships in db!");
        return friendshipsList;
    }

    @Override
    public Friendship save(Friendship entity) {
        Connection conn= dbUtils.getConnection();
        int result=0;
        try(PreparedStatement pre= conn.prepareStatement("insert into friendship(idUser1,idUser2,friendsFrom,status) values (?,?,?,?)")){
            pre.setInt(1,entity.getIdUser1());
            pre.setInt(2,entity.getIdUser2());
            pre.setString(3,entity.getFriendsFrom().toString());
            pre.setString(4,entity.getFriendshipStatus().name());
            result=pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result==0)
            throw new RepositoryException("Couldn't save friendship to database!");
        return entity;
    }

    @Override
    public Friendship delete(Integer id) {
        Connection conn=dbUtils.getConnection();
        int res=0;
        try(PreparedStatement pre=conn.prepareStatement("delete from friendship where id=?")){
            pre.setInt(1,id);
            res=pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(res==0)
            throw new RepositoryException("Couldn't delete friendship!");
        return null;
    }

    @Override
    public Friendship update(Friendship entity) {
        return null;
    }

    @Override
    public void updateFriendshipForUsers(Integer id1, Integer id2, FriendshipStatus status) {
        Connection conn=dbUtils.getConnection();
        int res=0;
        try(PreparedStatement preparedStatement=conn.prepareStatement("update friendship set status=? where idUser1=? and idUser2=? and status=?")){
            preparedStatement.setString(1,status.toString());
            preparedStatement.setInt(2,id1);
            preparedStatement.setInt(3,id2);
            preparedStatement.setString(4,"PENDING");
            res=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(res==0)
            throw new RepositoryException("Couldn't update friendship for the given id's");
    }

    @Override
    public void deleteFriendshipForUsers(Integer id1, Integer id2) {
        Connection conn=dbUtils.getConnection();
        int res=0;
        try(PreparedStatement preparedStatement=conn.prepareStatement("delete from friendship where status=? and((idUser1=? and idUser2=?) or (idUser1=? and idUser2=?))")){
            preparedStatement.setString(1,"ACCEPTED");
            preparedStatement.setInt(2,id1);
            preparedStatement.setInt(3,id2);
            preparedStatement.setInt(4,id2);
            preparedStatement.setInt(5,id1);
            res=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(res==0)
            throw new RepositoryException("Couldn't delete friendship for the given id's");
    }
}
