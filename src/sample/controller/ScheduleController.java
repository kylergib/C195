package sample.controller;

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
/**
 *
 * @author Kyle Gibson
 */
/**
 * a class that loads a window to view all appointments and customers
 */
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
    /**
     * @param customerList sets the customer table with customer information
     */
    public void setCustomerTable(ObservableList customerList) {
        customerTable.setItems(customerList);
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
    /**
     * @param actionEvent loads window to add a new customer
     */
    public void addCustomerClicked(ActionEvent actionEvent) throws IOException {
        loadCustomerWindow(actionEvent, "Add Customer");
    }
    /**
     * @param actionEvent loads a window to modify a customer record
     */
    public void modifyCustomerClicked(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            errorLabel.setText("No customer is selected");
            return;
        }
        CustomerController.currentCustomer = selectedCustomer;
        loadCustomerWindow(actionEvent, "Modify Customer");
    }
    /**
     * attempts to delete a selected customer
     */
    public void deleteCustomerClicked() throws SQLException {
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
    /**
     * @param actionEvent loads a customer window
     */
    public void loadCustomerWindow(ActionEvent actionEvent, String windowTitle) throws IOException {
        CustomerController.customerTitleVar = windowTitle;
        Parent root = FXMLLoader.load(getClass().getResource("/customer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @return customer that is selected in the customer table
     */
    public Customer getCustomerSelected() {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            return selectedCustomer;
        }
        return null;
    }
    /**
     * @param actionEvent loads a window to add an appointment
     */
    public void addAppointmentClicked(ActionEvent actionEvent) throws IOException {
        loadAppointmentWindow(actionEvent, "Add Appointment");
    }
    /**
     * @param actionEvent loads a window to modify an existing appointment
     */
    public void modifyAppointmentClicked(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = getAppointmentSelected();
        if (selectedAppointment != null) {
            AppointmentController.currentAppointment = selectedAppointment;
            loadAppointmentWindow(actionEvent, "Modify Appointment");
        } else {
            errorLabel.setText("No appointment is selected");
        }

    }
    /**
     * attempts to delete a selected appointment
     */
    public void deleteAppointmentClicked() throws SQLException {
        Appointment selectedAppointment = getAppointmentSelected();
        if (selectedAppointment != null) {
            AppointmentQuery.delete(selectedAppointment);
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        } else {
            errorLabel.setText("No appointment is selected");
        }
    }
    /**
     * @return Appointment that is selected in appointments table
     */
    public Appointment getAppointmentSelected() {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            return selectedAppointment;
        }
        return null;
    }
    /**
     * @param actionEvent loads a window to add or modify an appointment
     */
    public void loadAppointmentWindow(ActionEvent actionEvent, String windowTitle) throws IOException {
        AppointmentController.appointmentTitleVar = windowTitle;
        Parent root = FXMLLoader.load(getClass().getResource("/appointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 475);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * show all appointments in the appointments table
     */
    public void allAppointmentsButtonClicked() throws SQLException {
        monthAppointmentsButton.setSelected(false);
        weekAppointmentsButton.setSelected(false);
        ObservableList allAppointments = AppointmentQuery.getAllAppointments();
        setAppointmentTable(allAppointments);
    }
    /**
     * show only appointments in the current month in the appointment table
     */
    public void monthAppointmentsButtonClicked() throws SQLException {
        allAppointmentsButton.setSelected(false);
        weekAppointmentsButton.setSelected(false);
        ObservableList appointmentsForMonth = AppointmentQuery.getAllMonthAppointments();
        setAppointmentTable(appointmentsForMonth);
    }
    /**
     * show only appointments in the current week in the appointment table
     */
    public void weekAppointmentsButtonClicked() throws SQLException {
        allAppointmentsButton.setSelected(false);
        monthAppointmentsButton.setSelected(false);
        ObservableList weekAppointments = AppointmentQuery.getAllWeekAppointments();
        setAppointmentTable(weekAppointments);
    }
    /**
     * @param appointmentList fills the appointment table with data from appointmentList
     */
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
    /**
     * exits the app
     */
    public void exitButtonClicked() {
        Platform.exit();
    }
    /**
     * @param actionEvent loads the reports window
     */
    public void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 670, 600);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param actionEvent attempts to load selected customers table if one is selected
     */
    public void customerScheduleClicked(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            errorLabel.setText("No customer is selected");
            return;
        }
        CustomerScheduleController.currentCustomer = selectedCustomer;
        loadCustomerSchedule(actionEvent);
    }
    /**
     * @param actionEvent loads the selected customer's schedule
     */
    public void loadCustomerSchedule(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/customerSchedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 977, 506);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
}
