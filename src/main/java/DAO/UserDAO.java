package DAO;

import Model.User;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User>{
    @Override
    public ObservableList<User> get() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ObservableList<User> allUsers = FXCollections.observableArrayList();
        while(rs.next()){
            User user = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name")
            );
            allUsers.add(user);
        }

        return allUsers;
    }

    @Override
    public User get(int id) throws SQLException {
        return null;
    }


    public User get(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            return new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name")
            );
        }

        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        return false;
    }

    @Override
    public boolean update(User user) throws SQLException {
        return false;
    }
}
