package sample.Utilities;

import sample.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Kyle Gibson
 */
public abstract class UserQuery {
    public static User select(String userString, String passwordString) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, userString);
        ps.setString(2, passwordString);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) { //rs.next returns bool if values are next
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString(("Password"));
            User user = new User(userId,userName,password);
            return user;
        }
        return null;
    }
}
