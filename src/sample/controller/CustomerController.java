package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import sample.Utilities.CountryQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.DivisionQuery;
import sample.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
//TODO: set tab order
public class CustomerController implements Initializable {

    public ComboBox customerCountryComboBox;
    public ComboBox customerDivisionComboBox;
    public TextField customerNameText;
    public TextField customerNumberText;
    public TextField customerStreetAddressText;
    public TextField customerPostalText;
    public Label customerErrorLabel;
    public Label customerTitle;
    public static String customerTitleVar;
    public static Customer currentCustomer;
    public TextField customerIdText;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTitle.setText(customerTitleVar);
//        if (customerTitleVar == "Add Customer") {
        try {
            ObservableList<String> allCountries = CountryQuery.getAllCountries();
            customerCountryComboBox.setItems(allCountries.sorted());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (customerTitleVar == "Modify Customer") {
            String[] addressSplit = currentCustomer.getAddress().split(",", 3);
            String country = addressSplit[2].trim();
            String streetAddress = addressSplit[0].trim();
            String division = addressSplit[1].trim();

            customerCountryComboBox.setValue(country);
            try {
                setDivisions(country);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            customerDivisionComboBox.setValue(division);
            customerIdText.setText(String.valueOf(currentCustomer.getCustomerId()));
            customerNameText.setText(currentCustomer.getCustomerName());
            customerNumberText.setText(currentCustomer.getPhoneNumber());
            customerStreetAddressText.setText(streetAddress);
            customerPostalText.setText(currentCustomer.getPostalCode());

        }

    }

    public void saveCustomerClicked(ActionEvent actionEvent) throws SQLException, IOException {
        String customerCountry = (String) customerCountryComboBox.getValue();
        String customerDivision = (String) customerDivisionComboBox.getValue();
        String customerName = customerNameText.getText();
        String customerNumber = customerNumberText.getText();
        String customerStreetAddress = customerStreetAddressText.getText();
        String customerPostal = customerPostalText.getText();
        String address = customerStreetAddress + ", " + customerDivision + ", " + customerCountry;
        String[] checkStrings = {customerCountry,customerDivision,customerName,
                customerNumber,customerStreetAddress,customerPostal};
        for (int i =0; i < checkStrings.length; i++) {
            if (checkStrings[i] == null) {
                System.out.println("One of the fields is empty");
                customerErrorLabel.setText("One of the fields is empty");
                return;
            }
        }

        Customer newCustomer = new Customer(CustomerQuery.getAllCustomers().size() +1,
                customerName,address,customerPostal, customerNumber);
        System.out.println(newCustomer);
        //TODO: save customer to database

        if (newCustomer != null) {
            loadAppointments(actionEvent);
        }

    }

    public void cancelCustomerClicked(ActionEvent actionEvent) throws IOException {
        loadAppointments(actionEvent);
    }

    public void customerCountrySelected(ActionEvent actionEvent) throws SQLException {
        System.out.println("COMBO");
        setDivisions((String) customerCountryComboBox.getValue());
    }

    public void setDivisions(String countryName) throws SQLException {
        int countryId = CountryQuery.getCountryId(countryName);
        ObservableList allDivisions = DivisionQuery.getAllDivisionsFromCountry(countryId);
        customerDivisionComboBox.setItems(allDivisions.sorted());
        customerDivisionComboBox.setPromptText("");
        customerDivisionComboBox.setDisable(false);
    }

    public void loadAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1550, 600);
        stage.setTitle("typeVar");
        stage.setScene(scene);
        stage.show();
    }
}
