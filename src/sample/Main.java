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



        ObservableList allAppointments = AppointmentQuery.getAllAppointments();
        ObservableList<ReportType> allReports = FXCollections.observableArrayList();

//        System.out.println(allReports);
        for (int i = 0; i < allAppointments.size(); i++) {
            Appointment currentAppointment = (Appointment) allAppointments.get(i);
//            System.out.println(currentAppointment.getType());
            if (allReports.size() > 0) {
                Boolean addedType = false;
                for (int z = 0; z < allReports.size(); z++) {
                    System.out.println(allReports.get(z).getType() + "-" + currentAppointment.getType());
                    if (allReports.get(z).getType() == currentAppointment.getType()) {
                        System.out.println(allReports.get(z).getType() + "-" + currentAppointment.getType());
                        allReports.get(z).setTotal(allReports.get(z).getTotal() +1);
                        addedType = true;
//                        break;
                    }

                }
                if (addedType == false) {
                    System.out.println("NONE in");
                    int appointmentMonth = currentAppointment.getStart().toLocalDateTime().getMonthValue();
                    int appointmentYear = currentAppointment.getStart().toLocalDateTime().getYear();
                    ReportType newReport = new ReportType(currentAppointment.getType(),appointmentMonth,appointmentYear,1);
                    allReports.add(newReport);
//                    break;
                }
            } else {
                System.out.println("NONE");
                int appointmentMonth = currentAppointment.getStart().toLocalDateTime().getMonthValue();
                int appointmentYear = currentAppointment.getStart().toLocalDateTime().getYear();
                ReportType newReport = new ReportType(currentAppointment.getType(),appointmentMonth,appointmentYear,1);
                allReports.add(newReport);
            }

        }
        System.out.println(allReports);


//            if (allTypes.containsKey(currentAppointment.getType())) {
//                int currentTotal = allTypes.get(currentAppointment.getType());
//                allTypes.put(currentAppointment.getType(), currentTotal + 1 );
//            } else {
//                allTypes.put(currentAppointment.getType(), 1);
//            }
//        }
//        System.out.println(allTypes);




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
