package sample;

import com.sun.javafx.image.IntPixelGetter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.*;
import sample.model.Appointment;
import sample.model.ReportType;
import sample.model.User;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static ResourceBundle rb = ResourceBundle.getBundle("sample/Lang",Locale.getDefault());
    public static User myUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBConnection.openConnection();

        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();














//        DBConnection.closeConnection();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
