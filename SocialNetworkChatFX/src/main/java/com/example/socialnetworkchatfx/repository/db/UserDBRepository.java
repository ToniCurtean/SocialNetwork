package com.example.socialnetworkchatfx.repository.db;


import com.example.socialnetworkchatfx.domain.User;
import com.example.socialnetworkchatfx.repository.UserRepository;
import com.example.socialnetworkchatfx.repository.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class UserDBRepository implements UserRepository {
    private JdbcUtils dbUtils;

    public UserDBRepository(Properties properties) {

        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public User findOne(Integer id) {

        Connection conn=dbUtils.getConnection();
        User user=null;
        try(PreparedStatement preparedStatement= conn.prepareStatement("select * from user where id=?")){
            preparedStatement.setInt(1,id);
            try(ResultSet res=preparedStatement.executeQuery()){
                while(res.next()){
                    user=new User(res.getInt("id"),res.getString("firstName"),res.getString("lastName"),res.getString("email"),res.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user==null)
            throw new RepositoryException("Couldn't find the user with the given id");
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User save(User entity) {
        Connection conn= dbUtils.getConnection();
        int result=0;
        try(PreparedStatement pre= conn.prepareStatement("insert into user(firstName,lastName,email,password) values (?,?,?,?)")){
            pre.setString(1,entity.getFirstName());
            pre.setString(2,entity.getLastName());
            pre.setString(3,entity.getEmail());
            pre.setString(4,entity.getPassword());
            result=pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result==0)
            throw new RepositoryException("Couldn't save user to database!");
        return entity;
    }

    @Override
    public User delete(Integer integer) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User findByEmailPassword(String email, String password) {
        Connection conn=dbUtils.getConnection();
        User user=null;
        try(PreparedStatement preparedStatement= conn.prepareStatement("select * from user where email=? and password=?")){
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            try(ResultSet res=preparedStatement.executeQuery()){
                while(res.next()){
                    user=new User(res.getInt("id"),res.getString("firstName"),res.getString("lastName"),res.getString("email"),res.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user==null)
            throw new RepositoryException("Couldn't find the user with the given email and password");
        return user;
    }

    @Override
    public User findByName(String firstName, String lastName) {
        Connection conn=dbUtils.getConnection();
        User user=null;
        try(PreparedStatement preparedStatement= conn.prepareStatement("select * from user where firstName=? and lastName=?")){
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            try(ResultSet res=preparedStatement.executeQuery()){
                while(res.next()){
                    user=new User(res.getInt("id"),res.getString("firstName"),res.getString("lastName"),res.getString("email"),res.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user==null)
            throw new RepositoryException("Couldn't find the user with the given name ");
        return user;
    }
}
