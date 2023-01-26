package sample.Utilities;
//clean
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryQuery {

    public static int getCountryId(String countryName) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE Country = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getInt("Country_ID");
        }
        return -1;
    }
    public static String getCountryName(int countryId) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getString("Country");
        }
        return null;
    }
    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            allCountries.add(rs.getString("Country"));
        }
        return allCountries;
    }
}
