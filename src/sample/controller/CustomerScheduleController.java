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
import sample.model.Customer;
import sample.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 *
 * @author Kyle Gibson
 */
/**
 * a class that loads a window to view a customers schedule
 */
public class CustomerScheduleController implements Initializable {
    public static Customer currentCustomer;
    public TableView appointmentTable;
    public TableColumn appointmentIDColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startTimeColumn;
    public TableColumn endTimeColumn;
    public TableColumn appointmentTableCustomerIdColumn;
    public TableColumn userIdColumn;
    public Label customerNameLabel;
    public Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerNameLabel.setText(currentCustomer.getCustomerName());
        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = AppointmentQuery.getAllCustomerAppointments(currentCustomer.getCustomerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            setAppointmentTable(allAppointments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param actionEvent loads the main window and goes back ot it
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1640, 600);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param appointmentList sets appointment schedule for a specific customer
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
}
