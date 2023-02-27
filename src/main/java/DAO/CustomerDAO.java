package DAO;

import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO implements DAO <Customer> {
    @Override
    public ObservableList<Customer> get() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String sql = "SELECT Customer_ID, Customer_name, Address, Postal_Code, Phone, Division_ID FROM customers";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int oid = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(oid, name, address, postalCode, phone, divisionId);

            allCustomers.add(customer);

        }

        return allCustomers;
    }

    public Customer get(String name) throws SQLException {
        String sql = "SELECT Customer_ID, Customer_name, Address, Postal_Code, Phone, Division_ID FROM customers WHERE Customer_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            return new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID")
            );
        }

        return null;
    }

    @Override
    public Customer get(int id) throws SQLException {
        String sql = "SELECT Customer_ID, Customer_name, Address, Postal_Code, Phone, Division_ID FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            return new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID"));
        }
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        return true;
    }

    @Override
    public boolean insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Phone, Postal_Code, Division_ID) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPhone());
        ps.setString(4, customer.getPostalCode());
        ps.setInt(5, customer.getDivisionID());
        ps.executeUpdate();

        return true;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {

        String sql = "UPDATE customers " +
                "SET Customer_name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPhone());
        ps.setString(4, customer.getPostalCode());
        ps.setInt(5, customer.getDivisionID());
        ps.setInt(6, customer.getCustomerID());

        ps.executeUpdate();

        return true;
    }
}
