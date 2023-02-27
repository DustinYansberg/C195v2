package DAO;

import Model.Country;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryDAO implements DAO<Country> {


    @Override
    public ObservableList<Country> get() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        while(rs.next()){
            Country country = new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country"));
            allCountries.add(country);
        }
        return allCountries;
    }

    @Override
    public Country get(int id) throws SQLException {

        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Country country = null;

        if (rs.next()){
            country = new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country")
                    );
        }
        return country;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean insert(Country country) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Country country) throws SQLException {
        return false;
    }
}
