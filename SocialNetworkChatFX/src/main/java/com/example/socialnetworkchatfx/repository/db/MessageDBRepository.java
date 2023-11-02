package com.example.socialnetworkchatfx.repository.db;

import com.example.socialnetworkchatfx.domain.Friendship;
import com.example.socialnetworkchatfx.domain.FriendshipStatus;
import com.example.socialnetworkchatfx.domain.Message;
import com.example.socialnetworkchatfx.repository.MessageRepository;
import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MessageDBRepository implements MessageRepository {

    private JdbcUtils dbUtils;

    public MessageDBRepository(JdbcUtils jdbcUtils){

        this.dbUtils =jdbcUtils;
    }


    @Override
    public Message findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> messagesList=new ArrayList<>();
        Connection conn=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=conn.prepareStatement("select * from message")){
            try(ResultSet res=preparedStatement.executeQuery()){
                while(res.next()){
                    Message message=new Message(res.getInt("id"),res.getInt("idUser1"),res.getInt("idUser2"),res.getString("text"),LocalDateTime.parse(res.getString("timeT")));
                    messagesList.add(message);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(messagesList.isEmpty())
            throw new RepositoryException("No messages in db!");
        return messagesList;
    }

    @Override
    public Message save(Message entity) {
        Connection conn= dbUtils.getConnection();
        int result=0;
        try(PreparedStatement pre= conn.prepareStatement("insert into message(idUser1,idUser2,text,timeT) values (?,?,?,?)")){
            pre.setInt(1,entity.getIdUser1());
            pre.setInt(2,entity.getIdUser2());
            pre.setString(3,entity.getText());
            pre.setString(4,entity.getTime().toString());
            result=pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result==0)
            throw new RepositoryException("Couldn't save message to database!");
        return entity;
    }

    @Override
    public Message delete(Integer integer) {
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }

    @Override
    public List<Message> getMessagesForUsers(Integer id1, Integer id2) {
        List<Message> messages=new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preparedStatement= con.prepareStatement("select * from message where (idUser1=? and idUser2=?) or(idUser1=? and idUser2=?) order by timeT")){
            preparedStatement.setInt(1,id1);
            preparedStatement.setInt(2,id2);
            preparedStatement.setInt(3,id2);
            preparedStatement.setInt(4,id1);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Message message=new Message(resultSet.getInt("id"),resultSet.getInt("idUser1"),resultSet.getInt("idUser2"),resultSet.getString("text"),LocalDateTime.parse(resultSet.getString("timeT")));
                    messages.add(message);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messages;
    }


}
