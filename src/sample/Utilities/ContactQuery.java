package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Kyle Gibson
 */
public class ContactQuery {
    public static ObservableList<String> getAllContacts() throws SQLException {
        ObservableList<String> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.println("1");
        while(rs.next()) {
            String contactName = rs.getString("Contact_Name");
            allContacts.add(contactName);
        }
        return allContacts;
    }
    public static String getContactName(int contactId) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,contactId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String contactName = rs.getString("Contact_Name");
            return contactName;
        }
        return null;
    }
}
