package sample.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Querycopy {

    public static int insert(int colorID,String fruitName) throws SQLException {
        //works
        //INSERT INTO tablename (columnname1, columnname2) VALUES(?,?) -> ? lets you insert a list of items into sql statement
        String sql = "INSERT INTO FRUITS (Fruit_name, color_ID) VALUES(?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        //below: (bind variable index from sql var, value to insert )
        ps.setString(1,fruitName); //bind value (?) - 1
        ps.setInt(2,colorID); //bind value (?) - 2
        int rowsAffected = ps.executeUpdate(); //returns number of rows affected
        System.out.println();
        return rowsAffected;
    }

    public static int update(int fruitID, String fruitName) throws SQLException {
        //Works
        String sql = "UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ? ";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        //below: (bind variable index from sql var, value to insert )
        ps.setString(1,fruitName); //bind value (?) - 1
        ps.setInt(2,fruitID); //bind value (?) - 2
        int rowsAffected = ps.executeUpdate(); //returns number of rows affected
        System.out.println();
        return rowsAffected;
    }

    public static int delete(int fruitID) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        //below: (bind variable index from sql var, value to insert )
        ps.setInt(1,fruitID); //bind value (?) - 1
        int rowsAffected = ps.executeUpdate(); //returns number of rows affected
        System.out.println();
        return rowsAffected;
    }
    public static void select() throws SQLException {
        String sql = "SELECT * FROM FRUITS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) { //rs.next returns bool if values are next
            int fruitId = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            int colorId = rs.getInt("Color_ID");
            System.out.print(fruitId + " | " + fruitName  + " | " + colorId);
            System.out.println();
        }
    }
    public static void select(int colorID) throws SQLException {
        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, colorID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) { //rs.next returns bool if values are next
            int fruitId = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            int colorId = rs.getInt("Color_ID");
            System.out.print(fruitId + " | " + fruitName  + " | " + colorId);
            System.out.println();
        }
    }
}
