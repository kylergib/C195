package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.*;
import sample.model.User;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static ResourceBundle rb = ResourceBundle.getBundle("sample/Lang",Locale.getDefault());
    public static User myUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBConnection.openConnection();

//        Timestamp appointmentStart = Timestamp.valueOf("2023-01-26 22:00:00");
//        Timestamp appointmentStart = Timestamp.valueOf("2023-01-26 22:00:00");
//        if (appointmentStart.toLocalDateTime().getHour() < 8 || appointmentStart.toLocalDateTime().getHour() > 21) {
//
//        } else if (appointmentEnd.toLocalDateTime().getHour() < 8 || appointmentEnd.toLocalDateTime().getHour() > 21) {
//
//        }
//
//        System.out.println(appointmentStart.toLocalDateTime().getHour());


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
