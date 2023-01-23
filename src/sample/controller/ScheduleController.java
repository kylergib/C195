package sample.controller;

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
import sample.Utilities.CustomerQuery;
import sample.model.Appointment;
import sample.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentUserLabel.setText(myUser.getUserName());
        try {

            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();

            appointmentTable.setItems(allAppointments);

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


        //LEFT OFF trying to figure out the best way to get contact info from a contact id
            ObservableList<Customer> allCustomers = CustomerQuery.getAllCustomers();
            setCustomerTable(allCustomers);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void setCustomerTable(ObservableList customerList) {
        customerTable.setItems(customerList);
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//        customerDivisionId.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    public void addCustomerClicked(ActionEvent actionEvent) throws IOException {
        loadCustomerWindow(actionEvent, "Add Customer");
    }

    public void modifyCustomerClicked(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            System.out.println("No customer is selected");
            return;
        }
        CustomerController.currentCustomer = selectedCustomer;
        loadCustomerWindow(actionEvent, "Modify Customer");


    }

    public void deleteCustomerClicked(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = getCustomerSelected();
        if (selectedCustomer == null) {
            System.out.println("No customer is selected");
            return;
        } else {
            CustomerQuery.delete(selectedCustomer);
            customerTable.setItems(CustomerQuery.getAllCustomers());
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
        if (selectedAppointment == null) {
            System.out.println("No appointment is selected");

            return;
        }
        AppointmentController.currentAppointment = selectedAppointment;
        loadAppointmentWindow(actionEvent, "Modify Appointment");
    }

    public void deleteAppointmentClicked(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = getAppointmentSelected();
        if (selectedAppointment == null) {
            System.out.println("No customer is selected");
            return;
        } else {
            AppointmentQuery.delete(selectedAppointment);
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
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
}
