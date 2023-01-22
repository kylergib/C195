package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.InputMethodEvent;
import sample.Utilities.CountryQuery;
import sample.Utilities.DivisionQuery;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public ComboBox customerCountryComboBox;
    public ComboBox customerDivisionComboBox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> allCountries = CountryQuery.getAllCountries();
            customerCountryComboBox.setItems(allCountries.sorted());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCustomerClicked(ActionEvent actionEvent) {
    }

    public void cancelCustomerClicked(ActionEvent actionEvent) {
    }

    public void customerCountrySelected(ActionEvent actionEvent) throws SQLException {

        System.out.println("COMBO");
        setDivisions((String) customerCountryComboBox.getValue());


    }

    public void setDivisions(String countryName) throws SQLException {
        int countryId = CountryQuery.getCountryId(countryName);
        ObservableList allDivsisions = DivisionQuery.getAllDivisionsFromCountry(countryId);

        customerDivisionComboBox.setItems(allDivsisions.sorted());
    }
}
