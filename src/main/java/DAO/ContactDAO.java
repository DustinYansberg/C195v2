package DAO;

import Model.Contact;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactDAO implements DAO <Contact> {


    @Override
    public ObservableList<Contact> get() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){

            Contact contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
        );
            allContacts.add(contact);
        }

        return allContacts;
    }

    @Override
    public Contact get(int id) throws SQLException {
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Contact contact = null;
        if (rs.next()) {
            contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            );
        }
        return contact;
    }

    public Contact get(String name) throws SQLException{
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        Contact contact = null;
        if (rs.next()) {
            contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            );
        }
        return contact;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean insert(Contact contact) {
        return false;
    }

    @Override
    public boolean update(Contact contact) {
        return false;
    }
}
