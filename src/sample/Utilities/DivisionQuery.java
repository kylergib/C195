package sample.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionQuery {

    public static String getDivisionName(int divisionId, int countryId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ? and Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setInt(1, divisionId);
        ps.setInt(2, countryId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            return rs.getString("Division");
        }
        return null;

    }

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
}
