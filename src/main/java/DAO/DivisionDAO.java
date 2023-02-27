package DAO;

import Model.Division;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO implements DAO<Division> {
    @Override
    public ObservableList<Division> get() throws SQLException {


        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        while(rs.next()){
            Division division = new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            );
            allDivisions.add(division);
        }

        return allDivisions;
    }

    @Override
    public Division get(int id) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Division division = null;
        if (rs.next()){
            division = new Division (
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            );
        }
        return division;
    }

    public Division get(String name) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        Division division = null;
        if (rs.next()){
            division = new Division (
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            );
        }
        return division;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean insert(Division division) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Division division) throws SQLException {
        return false;
    }
}
