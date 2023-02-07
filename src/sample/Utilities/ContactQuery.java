package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Kyle Gibson
 */
/**
 * Class to query the contact table in database
 */
public class ContactQuery {
    /**
     * @return list of all contacts
     */
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
    public static ObservableList<Contact> getAllContactsObject() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.println("1");
        while(rs.next()) {
            String contactName = rs.getString("Contact_Name");
            int contactId = rs.getInt("Contact_ID");
            Contact newContact = new Contact(contactId,contactName);
            allContacts.add(newContact);
        }
        return allContacts;
    }
    /**
     * @return name of a contact
     */
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
