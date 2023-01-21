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
            System.out.println("2");
            String address = rs.getString("Address");
            System.out.println("3");
            String postalCode = rs.getString("Postal_Code");
            System.out.println("4");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
//            int countryId = rs.getInt("Country_ID");
            String divisionString = DivisionQuery.getDivisionName(divisionId);
            int countryId = DivisionQuery.getCountryId(divisionId);
            String countryString = CountryQuery.getCountryName(countryId);
            System.out.println(divisionString + " " + countryString);
            String newAddress = address + ", " + divisionString + ", " + countryString;






            Customer newCustomer = new Customer(id,customerName,newAddress,
                    postalCode,phone);
            allCustomers.add(newCustomer);
        }
        return allCustomers;
    }
}
