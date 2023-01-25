package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Utilities.AppointmentQuery;
import sample.model.Appointment;
import sample.model.ReportType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    public TableView monthTypeTable;
    public TableColumn monthColumn;
    public TableColumn typeColumn;
    public TableColumn totalColumn;
    public TableColumn yearColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList allAppointments = null;
        try {
            allAppointments = AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<ReportType> allReports = FXCollections.observableArrayList();

//        System.out.println(allReports);
        System.out.println(1);
        for (int i = 0; i < allAppointments.size(); i++) {

            System.out.println(2);
            Appointment currentAppointment = (Appointment) allAppointments.get(i);
            int appointmentMonth = currentAppointment.getStart().toLocalDateTime().getMonthValue();
            int appointmentYear = currentAppointment.getStart().toLocalDateTime().getYear();
//            System.out.println(currentAppointment.getType());
            if (allReports.size() > 0) {
                Boolean addedType = false;
                for (int z = 0; z < allReports.size(); z++) {
                    System.out.println(3);

                    System.out.println(allReports.get(z).getType() + "-" + currentAppointment.getType());
                    System.out.println(allReports.get(z).getType() == currentAppointment.getType());
                    System.out.println(String.valueOf(allReports.get(z).getType()) == String.valueOf(currentAppointment.getType()));
                    System.out.println("asdf" == "asdf");
                    String allReportString = String.valueOf(allReports.get(z).getType());
                    String currentReportString = String.valueOf(currentAppointment.getType());
                    System.out.println(allReportString == "asdf");
                    if (allReports.get(z).getType().equals(currentAppointment.getType()) && allReports.get(z).getMonth() == appointmentMonth &&
                            allReports.get(z).getYear() == appointmentYear) {
                        System.out.println(4);
                        System.out.println(allReports.get(z).getType() + "-" + currentAppointment.getType());
                        allReports.get(z).setTotal(allReports.get(z).getTotal() +1);
                        addedType = true;
//                        break;
                    }

                }
                if (addedType == false) {
                    System.out.println("NONE in");

                    ReportType newReport = new ReportType(currentAppointment.getType(),appointmentMonth,appointmentYear,1);
                    allReports.add(newReport);
//                    break;
                }
            } else {
                System.out.println("NONE");
//                appointmentMonth = currentAppointment.getStart().toLocalDateTime().getMonthValue();
//                int appointmentYear = currentAppointment.getStart().toLocalDateTime().getYear();
                ReportType newReport = new ReportType(currentAppointment.getType(),appointmentMonth,appointmentYear,1);
                allReports.add(newReport);
            }

        }
        System.out.println(allReports);
        Comparator<ReportType> byYear = Comparator.comparingInt(ReportType::getYear);
        Comparator<ReportType> byMonth = Comparator.comparingInt(ReportType::getMonth);
        Comparator<ReportType> compareDate = byYear.reversed().thenComparing(byMonth.reversed());

        monthTypeTable.setItems(allReports.sorted(compareDate));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));


    }

    public void scheduleButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1640, 600);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public void exitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}