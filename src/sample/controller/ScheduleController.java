package sample.controller;
// cleaned
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.CustomerQuery;
import sample.model.Appointment;
import sample.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static sample.Main.myUser;

public class ScheduleController implements Initializable {
    public Label userLabel;
    public Label currentUserLabel;
    public TableView appointmentTable;
    public TableColumn appointmentIDColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startTimeColumn;
    public TableColumn endTimeColumn;
    public TableColumn customerIdColumn;
    public TableColumn userIdColumn;
    public TableView customerTable;
    public TableColumn appointmentTableCustomerIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn customerAddressColumn;
    public TableColumn customerPostalCodeColumn;
    public TableColumn customerPhoneColumn;
    public TableColumn customerDivisionId;
    public Button addCustomer;
    public Button modifyCustomer;
    public Button deleteCustomer;
    public static Boolean firstStart = true;
    public Label errorLabel;
    public ToggleButton allAppointmentsButton;
    public ToggleButton monthAppointmentsButton;
    public ToggleButton weekAppointmentsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUserLabel.setText(myUser.getUserName());
        try {
            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
            setAppointmentTable(allAppointments);
            ObservableList<Customer> allCustomers = CustomerQuery.getAllCustomers();
            setCustomerTable(allCustomers);
            if (firstStart == true) {
                Boolean appointmentClose = false;
                for (int i = 0; i < allAppointments.size(); i++) {
                    Appointment thisAppointment = allAppointments.get(i);
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    LocalDateTime currentTimeDate = currentTime.toLocalDateTime();
                    LocalDateTime fifteenMinutes = currentTimeDate.plus(15, ChronoUnit.MINUTES);
                    LocalDateTime appointmentDateTime = thisAppointment.getStart().toLocalDateTime();
                    Boolean appointmentBetween = (appointmentDateTime.isAfter(currentTimeDate) && appointmentDateTime.isBefore(fifteenMinutes));
                    if (appointmentBetween && thisAppointment.getUserId() == myUser.getUserId()) {
                        String messageLabel = "You have an appointment within 15 minutes!\n" +
                                "Appointment ID: " + thisAppointment.getId() + "\nTime: " + thisAppointment.getStart();
                        openDialog(messageLabel, "fifteen");
                        appointmentClose = true;
                        firstStart = false;
                    }
                }
                if (appointmentClose == false) {
                    openDialog("You have no upcoming appointments ", "fifteen");
                    firstStart = false;
                }
                firstStart = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void openDialog(String labelText, String dialogType) throws IOException {
        DialogController.newLabelText = labelText;
        DialogController.dialogType = dialogType;
        Parent root = FXMLLoader.load(getClass().getResource("/dialog.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void setCustomerTable(ObservableList customerList) {
        customerTable.setItems(customerList);
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
    public void addCustomerClicked(ActionEvent actionEvent) throws IOException {
        loadCustomerWindow(actionEvent, "Add Customer");
    }
    public void modifyCustomerClicked(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            errorLabel.setText("No customer is selected");
            return;
        }
        CustomerController.currentCustomer = selectedCustomer;
        loadCustomerWindow(actionEvent, "Modify Customer");
    }
    public void deleteCustomerClicked(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            errorLabel.setText("No customer is selected");
            return;
        }
        int customerAppointments = AppointmentQuery.getCustomerAppointments(selectedCustomer.getCustomerId());
        if (customerAppointments > 0) {
            errorLabel.setText("Please cancel any appointments for this customer before trying to delete them.");
        } else {
            CustomerQuery.delete(selectedCustomer);
            customerTable.setItems(CustomerQuery.getAllCustomers());
            errorLabel.setText("Successfully deleted customer");
        }
    }
    public void loadCustomerWindow(ActionEvent actionEvent, String windowTitle) throws IOException {
        CustomerController.customerTitleVar = windowTitle;
        Parent root = FXMLLoader.load(getClass().getResource("/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }
    public Customer getCustomerSelected() {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            return selectedCustomer;
        }
        return null;
    }
    public void addAppointmentClicked(ActionEvent actionEvent) throws IOException {
        loadAppointmentWindow(actionEvent, "Add Appointment");
    }
    public void modifyAppointmentClicked(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = getAppointmentSelected();
        if (selectedAppointment != null) {
            AppointmentController.currentAppointment = selectedAppointment;
            loadAppointmentWindow(actionEvent, "Modify Appointment");
        } else {
            errorLabel.setText("No appointment is selected");
        }

    }
    public void deleteAppointmentClicked(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = getAppointmentSelected();
        if (selectedAppointment != null) {
            AppointmentQuery.delete(selectedAppointment);
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        } else {
            errorLabel.setText("No appointment is selected");
        }
    }
    public Appointment getAppointmentSelected() {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            return selectedAppointment;
        }
        return null;
    }
    public void loadAppointmentWindow(ActionEvent actionEvent, String windowTitle) throws IOException {
        AppointmentController.appointmentTitleVar = windowTitle;
        Parent root = FXMLLoader.load(getClass().getResource("/appointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 475);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }
    public void allAppointmentsButtonClicked(ActionEvent actionEvent) throws SQLException {
        monthAppointmentsButton.setSelected(false);
        weekAppointmentsButton.setSelected(false);
        ObservableList allAppointments = AppointmentQuery.getAllAppointments();
        setAppointmentTable(allAppointments);
    }
    public void monthAppointmentsButtonClicked(ActionEvent actionEvent) throws SQLException {
        allAppointmentsButton.setSelected(false);
        weekAppointmentsButton.setSelected(false);
        ObservableList appointmentsForMonth = AppointmentQuery.getAllMonthAppointments();
        setAppointmentTable(appointmentsForMonth);
    }
    public void weekAppointmentsButtonClicked(ActionEvent actionEvent) throws SQLException {
        allAppointmentsButton.setSelected(false);
        monthAppointmentsButton.setSelected(false);
        ObservableList weekAppointments = AppointmentQuery.getAllWeekAppointments();
        setAppointmentTable(weekAppointments);
    }
    public void setAppointmentTable(ObservableList<Appointment> appointmentList) throws SQLException {
        appointmentTable.setItems(appointmentList);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentTableCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
    public void exitButtonClicked() {
        Platform.exit();
    }
    public void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 670, 600);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    public void customerScheduleClicked(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            errorLabel.setText("No customer is selected");
            return;
        }
        CustomerScheduleController.currentCustomer = selectedCustomer;
        loadCustomerSchedule(actionEvent);
    }
    public void loadCustomerSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/customerSchedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 977, 506);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
}
