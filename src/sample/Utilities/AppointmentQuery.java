package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;
import sample.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
/**
 *
 * @author Kyle Gibson
 */
/**
 * Class to query the appointment table in database
 */
public abstract class AppointmentQuery {

    /**
     * @return observable list of type appointment and gets all appointments
     */
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
    /**
     * @return observable list of type appointment and gets all appointments for this month
     */
    public static ObservableList<Appointment> getAllMonthAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Start BETWEEN ? and ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
        System.out.println(currentTimeDate.getYear() + " " + currentTimeDate.getMonthValue());
        YearMonth yearMonth = YearMonth.of(currentTimeDate.getYear(),currentTimeDate.getMonthValue());
        LocalDateTime lastDayLocal = yearMonth.atEndOfMonth().atTime(11,59,00);
        LocalDateTime firstDayLocal = yearMonth.atDay(1).atTime(0,0,1);
        Timestamp firstDay = Timestamp.valueOf(firstDayLocal);
        Timestamp lastDay = Timestamp.valueOf(lastDayLocal);
        ps.setTimestamp(1,firstDay);
        ps.setTimestamp(2,lastDay);
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
    /**
     * @return observable list of type appointment and gets all appointments for this week
     */
    public static ObservableList<Appointment> getAllWeekAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Start BETWEEN ? and ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDate startTimeDate = currentTime.toLocalDateTime().toLocalDate();
        LocalDate endTimeDate = currentTime.toLocalDateTime().toLocalDate();
        startTimeDate.minusDays(1);
        while (startTimeDate.getDayOfWeek() != DayOfWeek.MONDAY){
            startTimeDate = startTimeDate.minusDays(1);
        }
        while (endTimeDate.getDayOfWeek() != DayOfWeek.FRIDAY){
            endTimeDate = endTimeDate.plusDays(1);
        }
        Timestamp endDateAndTime = Timestamp.valueOf(String.valueOf(endTimeDate)+" "+"11:59:00");
        Timestamp startDateAndTime = Timestamp.valueOf(String.valueOf(startTimeDate)+" "+"0:0:01");
        ps.setTimestamp(1,startDateAndTime);
        ps.setTimestamp(2,endDateAndTime);
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
    /**
     * @return number of rows affected when inserting an appointment into the database
     */
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
    /**
     * @return number of rows affected when inserting an appointment into the database
     */
    public static int delete(Appointment newAppointment) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, newAppointment.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * @return number of rows affected when inserting an appointment into the database
     */
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
        ps.setString(8, Main.myUser.getUserName());
        ps.setInt(9,newAppointment.getCustomerId());
        ps.setInt(10,Main.myUser.getUserId());
        ps.setInt(11,newAppointment.getContactId());
        ps.setInt(12,newAppointment.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * @return number of total appointments for a customer
     */
    public static int getCustomerAppointments(int customerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
       return -1;
    }
    /**
     * @return observable list of all appointments for a customer
     */
    public static ObservableList getAllCustomerAppointments(int currentCustomerId) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,currentCustomerId);
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
    /**
     * @return boolean if a customer already has an appointment for a certain time
     */
    public static Boolean checkConflictingAppointments(int myCustomerId, Timestamp appointmentTime) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,myCustomerId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            if (appointmentTime.equals(start) || appointmentTime.equals(end)) {
                return true;
            } else if (appointmentTime.before(end) && appointmentTime.after(start)) {
                System.out.println("BETWEEN");
                return true;
            }
    }
        return false;
    }

    public static ObservableList getAllContactAppointments(int currentContactID)  throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,currentContactID);
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
}
