package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.UserQuery;
import sample.model.Appointment;
import sample.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.Main.rb;
import static sample.Main.myUser;

public class LoginController implements Initializable {
    public Label titleLabel;
    public TextField userNameTextField;
    public PasswordField passwordTextField;
    public Button loginButton;
    public Label loginErrorLabel;
    public Label currentTimeZoneLabel;
    public Button languageToEnglishButton;
    public Button languageToFrenchButton;
    public Label timezoneLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrentTimeZone();
    }

    public void getCurrentTimeZone() {
        currentTimeZoneLabel.setText(String.valueOf(ZoneId.systemDefault()));
    }

    public void loginButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        System.out.println("Login button clicked");
//        System.out.println(userNameTextField.getText());
//        System.out.println(passwordTextField.getText());
        myUser = UserQuery.select(userNameTextField.getText(),passwordTextField.getText());
        if (myUser != null) {
            System.out.println("MYUSER = " + myUser.getUserName());
//            Parent root = FXMLLoader.load(getClass().getResource("view/appointments.fxml"));
//
//            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root, 800, 600);
//            stage.setTitle("Appointments");
//            stage.setScene(scene);
//            stage.show();

            try {
                System.out.println("Before Loaded");
                Parent root = FXMLLoader.load(getClass().getResource("/appointments.fxml"));
                System.out.println("Loaded");
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                System.out.println("Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();");
                Scene scene = new Scene(root, 1550, 600);
                System.out.println("Scene scene = new Scene(root, 1100, 650);");
                stage.setTitle("typeVar");
                System.out.println("stage.setTitle(\"typeVar\");");

                stage.setScene(scene);
                System.out.println("stage.setScene(scene);");
                stage.show();
            } catch (Exception e) {
                System.out.println(e);
            }


        } else {
            System.out.println("Please check username or password.");
            loginErrorLabel.setText(rb.getString("error"));
        }




    }

    public void languageToEnglishButtonClicked(ActionEvent actionEvent) {
        System.out.println("English clicked");
        languageToFrenchButton.setDisable(false);
        languageToEnglishButton.setDisable(true);
        rb = ResourceBundle.getBundle("sample/Lang",Locale.ENGLISH);
        setLanguage();
    }

    public void languageToFrenchButtonClicked(ActionEvent actionEvent) {
        System.out.println("French clicked");
        languageToFrenchButton.setDisable(true);
        languageToEnglishButton.setDisable(false);
        rb = ResourceBundle.getBundle("sample/Lang",Locale.FRENCH);
        setLanguage();
    }
    public void setLanguage() {
        loginButton.setText(rb.getString("login"));
        titleLabel.setText(rb.getString("pleaseLogin"));
        timezoneLabel.setText(rb.getString("timezone"));
        loginErrorLabel.setText("");
    }
}
