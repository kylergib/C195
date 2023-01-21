package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.CustomerQuery;
import sample.model.Appointment;
import sample.model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Main.myUser;

public class AppointmentController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("TESTING appointments");
        currentUserLabel.setText(myUser.getUserName());
        try {
            System.out.println("TESTING sql appointments");
            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();

            appointmentTable.setItems(allAppointments);
            System.out.println("after appointments");
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            System.out.println("after appointments title");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            System.out.println("after appointments desc");
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            System.out.println("after appointments loc");
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            System.out.println("after appointments con");
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            System.out.println("after appointments type");
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            System.out.println("after appointments start");
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            System.out.println("after appointments end");
            appointmentTableCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            System.out.println("after appointments cust");
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            System.out.println("after appointments user");

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
}
