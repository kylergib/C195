package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.CountryQuery;
import sample.Utilities.DBConnection;
import sample.Utilities.DivisionQuery;
import sample.Utilities.UserQuery;
import sample.model.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static ResourceBundle rb = ResourceBundle.getBundle("sample/Lang",Locale.getDefault());
    public static User myUser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        DBConnection.openConnection();
        System.out.println(CountryQuery.getCountryName(3));
        System.out.println(CountryQuery.getCountryId("U.S"));
        System.out.println(DivisionQuery.getDivisionName(4,1));
        System.out.println(DivisionQuery.getDivisionId("Colorado"));
        System.out.println(DivisionQuery.getCountryId("Yukon"));
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        //--------
        //testing sql statements
        //ALL WORK SO FAR
//        int rowsAffected = Query.insert(1, "Cherries");
//        System.out.println(rowsAffected);

//        int rowsAffected = Query.update(7, "Red Peppers");
//        System.out.println(rowsAffected);
//        int rowsAffected = Query.delete(8);
//        System.out.println(rowsAffected);
//        Query.delete(9);

//        Query.select();
//        System.out.println();
//        Query.select(3);
        //--------










//        DBConnection.closeConnection();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
