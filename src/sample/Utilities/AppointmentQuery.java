package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class AppointmentQuery {


    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");


            Appointment newAppointment = new Appointment(id, title, description, location,
                    type, start, end, customerId, userId, contactId);
            allAppointments.add(newAppointment);
        }
        return allAppointments;
    }

//    public ObservableList getAllAppointments() {
//        return allAppointments;
//    }

//    ResultSet rs = ps.executeQuery();
//        while(rs.next()) { //rs.next returns bool if values are next
//        int userId = rs.getInt("User_ID");
//        String userName = rs.getString("User_Name");
//        String password = rs.getString(("Password"));
//
//        System.out.println(userId + " | " + userName + " | " +  password);
//        User user = new User(userId,userName,password);
//        return user;

}
