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
/**
 * Class to query the division table in database
 */
public class DivisionQuery {
    /**
     * @return division name
     */
    public static String getDivisionName(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getString("Division");
        }
        return null;
    }
    /**
     * @return division id
     */
    public static int getDivisionId(String divisionName) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getInt("Division_ID");
        }
        return -1;
    }
    /**
     * @return country id
     */
    public static int getCountryId(String divisionName) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getInt("Country_ID");
        }
        return -1;
    }
    /**
     * @return country id
     */
    public static int getCountryId(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getInt("Country_ID");
        }
        return -1;
    }
    /**
     * @return list of all divisions in a specific country
     */
    public static ObservableList<String> getAllDivisionsFromCountry(int countryId) throws SQLException {
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            allDivisions.add(rs.getString("Division"));
        }
        return allDivisions;
    }
}
