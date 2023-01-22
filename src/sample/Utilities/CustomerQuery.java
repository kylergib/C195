package sample.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerQuery {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.println("1");
        while(rs.next()) {
            int id = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            String divisionString = DivisionQuery.getDivisionName(divisionId);
            int countryId = DivisionQuery.getCountryId(divisionId);
            String countryString = CountryQuery.getCountryName(countryId);
            String newAddress = address + ", " + divisionString + ", " + countryString;
            Customer newCustomer = new Customer(id,customerName,newAddress,
                    postalCode,phone);
            allCustomers.add(newCustomer);
        }
        return allCustomers;
    }
    public static int insert(Customer newCustomer) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code,Phone, Division_ID) VALUES(?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, newCustomer.getCustomerName());

        String[] addressSplit = newCustomer.getAddress().split(",", 3);
//        String country = addressSplit[2].trim();
        String streetAddress = addressSplit[0].trim();
        String division = addressSplit[1].trim();

        ps.setString(2,streetAddress);
        ps.setString(3, newCustomer.getPostalCode());
        ps.setString(4, newCustomer.getPhoneNumber());
        ps.setInt(5,DivisionQuery.getDivisionId(division));
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int delete(Customer newCustomer) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, newCustomer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(Customer newCustomer) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, newCustomer.getCustomerName());

        String[] addressSplit = newCustomer.getAddress().split(",", 3);
//        String country = addressSplit[2].trim();
        String streetAddress = addressSplit[0].trim();
        String division = addressSplit[1].trim();

        ps.setString(2,streetAddress);
        ps.setString(3, newCustomer.getPostalCode());
        ps.setString(4, newCustomer.getPhoneNumber());
        ps.setInt(5,DivisionQuery.getDivisionId(division));
        ps.setInt(6,newCustomer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
}
