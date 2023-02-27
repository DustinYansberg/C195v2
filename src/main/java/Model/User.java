package Model;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class User {

    int userId;
    String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //        get user ID associated with provided username
    public int userName(String userName) throws SQLException {

        // build SQL query, then execute it filling the wildcard with the provided username
        String sql = "SELECT User_Id FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);

//        put result into a resultset then "loop" through the set and assign the values to output
        ResultSet rs = ps.executeQuery();
    int output = 0;
        while(rs.next()){
            output = rs.getInt("User_Id");
        }
         
//        return the value found in the result set
    return output;
    }

    public int password(String password) throws SQLException {

        // build SQL query, then execute it filling the wildcard with the provided username
        String sql = "SELECT User_Id FROM USERS WHERE Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, password);

//        put result into a result set then "loop" through the set and assign the values to output
        ResultSet rs = ps.executeQuery();
        int output = 0;
        while(rs.next()){
            output = rs.getInt("User_Id");
        }

//        return the value found in the result set
        return output;
    }
}
