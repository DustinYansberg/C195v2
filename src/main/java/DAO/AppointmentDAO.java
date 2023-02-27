package DAO;

import Model.Appointment;
import Model.Contact;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDAO implements DAO<Appointment>{


    @Override
    public ObservableList<Appointment> get() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "Customer_ID, User_ID, Contact_ID FROM appointments";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){

            Appointment appointment = null;
            int oid = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");


            appointment = new Appointment(oid, title, description, location, type, start, end, customerId, userId, contactId);

            allAppointments.add(appointment);
        }

        return allAppointments;
    }

    @Override
    public Appointment get(int id) throws SQLException {
        Appointment appointment = null;
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "Customer_ID, User_ID, Contact_ID FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            int oid = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            appointment = new Appointment(oid, title, description, location, type, start, end, customerId, userId, contactId);
        }

        return appointment;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
        return true;
    }

    @Override
    public boolean insert(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, USER_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        ps.setInt(7,appointment.getCustomerID());
        ps.setInt(8, appointment.getUserID());
        ps.setInt(9, appointment.getContactID());

        ps.executeUpdate();


        return true;
    }

    @Override
    public boolean update(Appointment appointment) throws SQLException {

        String sql = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                "End = ?, Customer_ID = ?, USER_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf("2008-11-11 13:23:44"));
        ps.setTimestamp(6, Timestamp.valueOf("2008-11-11 13:23:44"));
        ps.setInt(7,appointment.getCustomerID());
        ps.setInt(8, appointment.getUserID());
        ps.setInt(9, appointment.getContactID());
        ps.setInt(10, appointment.getAppointmentID());

        ps.executeUpdate();

        return true;
    }
}
