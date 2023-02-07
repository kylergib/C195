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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.ContactQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.GetAverageTime;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
/**
 *
 * @author Kyle Gibson
 */
/**
 * a class that creates a window to view reports
 */
public class ReportController implements Initializable {

    public TableView monthTypeTable;
    public TableColumn monthColumn;
    public TableColumn typeColumn;
    public TableColumn totalColumn;
    public TableColumn yearColumn;
    public TableView customerAverageTable;
    public TableColumn averageCustomerId;
    public TableColumn averageTime;
    public TableColumn averageAppointments;
    public Button scheduleButton;
    public Button exitButton;
    public Button viewContactSchedule;
    public TableColumn contactNameColumn;
    public TableColumn contactIdColumn;
    public TableView contactTable;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setAverageTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList allAppointments;
        try {
            allAppointments = AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<ReportType> allReports = FXCollections.observableArrayList();
        for (int i = 0; i < allAppointments.size(); i++) {
            Appointment currentAppointment = (Appointment) allAppointments.get(i);
            int appointmentMonth = currentAppointment.getStart().toLocalDateTime().getMonthValue();
            int appointmentYear = currentAppointment.getStart().toLocalDateTime().getYear();
            if (allReports.size() > 0) {
                Boolean addedType = false;
                for (int z = 0; z < allReports.size(); z++) {

//                    String allReportString = String.valueOf(allReports.get(z).getType());
//                    String currentReportString = String.valueOf(currentAppointment.getType());
                    if (allReports.get(z).getType().equals(currentAppointment.getType()) && allReports.get(z).getMonth() == appointmentMonth &&
                            allReports.get(z).getYear() == appointmentYear) {
                        allReports.get(z).setTotal(allReports.get(z).getTotal() + 1);
                        addedType = true;
                    }
                }
                if (addedType == false) {
                    ReportType newReport = new ReportType(currentAppointment.getType(), appointmentMonth, appointmentYear, 1);
                    allReports.add(newReport);
                }
            } else {
                ReportType newReport = new ReportType(currentAppointment.getType(), appointmentMonth, appointmentYear, 1);
                allReports.add(newReport);
            }

        }
        Comparator<ReportType> byYear = Comparator.comparingInt(ReportType::getYear);
        Comparator<ReportType> byMonth = Comparator.comparingInt(ReportType::getMonth);
        Comparator<ReportType> compareDate = byYear.reversed().thenComparing(byMonth.reversed());
        monthTypeTable.setItems(allReports.sorted(compareDate));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));


        try {
            setContactTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * loads the schedule window
     * @param actionEvent event from pushing the button
     */
    public void scheduleButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedule.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1640, 600);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * exits out of the application on button click
     */
    public void exitButtonClicked() {
        Platform.exit();
    }
    /**
     * fills the table with averages of customer appointment times
     */
    public void setAverageTable() throws SQLException {
        ObservableList allCustomers = CustomerQuery.getAllCustomers();
        ObservableList allAverages = FXCollections.observableArrayList();
        for (int i = 0; i < allCustomers.size(); i++) {
            Customer currentCustomer = (Customer) allCustomers.get(i);
            ObservableList allCustomerAppointments = AppointmentQuery.getAllCustomerAppointments(currentCustomer.getCustomerId());
            long totalTime = 0;
            int totalAppointments = allCustomerAppointments.size();
            if (totalAppointments > 0) {
                for (int z = 0; z < totalAppointments; z++) {
                    Appointment currentAppointment = (Appointment) allCustomerAppointments.get(z);
                    totalTime = totalTime + (currentAppointment.getEnd().getTime() - currentAppointment.getStart().getTime()) / 60000;
                }
                GetAverageTime customerAverageTime = (time, total) -> (time / total);
                CustomerAverageTime newCustomer = new CustomerAverageTime(currentCustomer.getCustomerId(),
                        customerAverageTime.averageTime(totalTime, Integer.toUnsignedLong(totalAppointments)), totalAppointments);
                allAverages.add(newCustomer);
            } else {
                CustomerAverageTime newCustomer = new CustomerAverageTime(currentCustomer.getCustomerId(),
                        0, totalAppointments);
                allAverages.add(newCustomer);
            }
        }
        customerAverageTable.setItems(allAverages);
        averageCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        averageTime.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        averageAppointments.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));
    }



    public void setContactTable() throws SQLException {
        ObservableList<Contact> allContacts = ContactQuery.getAllContactsObject();

        contactTable.setItems(allContacts);
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));


    }

    /**
     * attempts to load selected contacts table if one is selected
     * @param actionEvent event from pushing the button
     */
    public void viewContactScheduleClicked(ActionEvent actionEvent) throws IOException {
        Contact selectedContact = getContactSelected();
        if (selectedContact == null) {
            errorLabel.setText("No customer is selected");
            return;
        }
        CustomerScheduleController.currentCustomer = selectedContact;
        loadCustomerSchedule(actionEvent);
    }
    /**
     * loads the selected customer's schedule
     * @param actionEvent event from pushing the button
     */
    public void loadCustomerSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/customerSchedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 977, 506);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    public Contact getContactSelected() {
        if (contactTable.getSelectionModel().getSelectedItem() != null) {
            Contact selectedContact = (Contact) contactTable.getSelectionModel().getSelectedItem();
            return selectedContact;
        }
        return null;
    }
}
