package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;
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
    public static ObservableList<Appointment> getAllMonthAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Start BETWEEN ? and ?";
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
    public static int insert(Appointment newAppointment) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1,newAppointment.getTitle());
        ps.setString(2,newAppointment.getDescription());
        ps.setString(3,newAppointment.getLocation());
        ps.setString(4,newAppointment.getType());
        ps.setTimestamp(5,newAppointment.getStart());
        ps.setTimestamp(6,newAppointment.getEnd());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(7,currentTime);
        ps.setString(8, Main.myUser.getUserName());
        ps.setTimestamp(9,currentTime);
        ps.setString(10, Main.myUser.getUserName());
        ps.setInt(11,newAppointment.getCustomerId());
        ps.setInt(12,Main.myUser.getUserId());
        ps.setInt(13,newAppointment.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete(Appointment newAppointment) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, newAppointment.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int update(Appointment newAppointment) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?,Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1,newAppointment.getTitle());
        ps.setString(2,newAppointment.getDescription());
        ps.setString(3,newAppointment.getLocation());
        ps.setString(4,newAppointment.getType());
        ps.setTimestamp(5,newAppointment.getStart());
        ps.setTimestamp(6,newAppointment.getEnd());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(7,currentTime);
//        ps.setString(6, Main.myUser.getUserName());
//        ps.setTimestamp(7,currentTime);
        ps.setString(8, Main.myUser.getUserName());
        ps.setInt(9,newAppointment.getCustomerId());
        ps.setInt(10,Main.myUser.getUserId());
        ps.setInt(11,newAppointment.getContactId());
        ps.setInt(12,newAppointment.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
    public static int getCustomerAppointments(int customerId) throws SQLException {
//        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            return rs.getInt(1);
        }
       return -1;
//       int id = rs.getInt("Appointment_ID");
//            String title = rs.getString("Title");
//            String description = rs.getString("Description");
//            String location = rs.getString("Location");
//            String type = rs.getString("Type");
//            Timestamp start = rs.getTimestamp("Start");
//            Timestamp end = rs.getTimestamp("End");
//            int customerId = rs.getInt("Customer_ID");
//            int userId = rs.getInt("User_ID");
//            int contactId = rs.getInt("Contact_ID");
//
//
//            Appointment newAppointment = new Appointment(id, title, description, location,
//                    type, start, end, customerId, userId, contactId);
//            allAppointments.add(newAppointment);
//        }
//        return allAppointments;
//        return rs;
    }

}
