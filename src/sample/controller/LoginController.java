package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Utilities.UserQuery;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.Main.rb;
import static sample.Main.myUser;
//cleaned
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
        myUser = UserQuery.select(userNameTextField.getText(),passwordTextField.getText());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (myUser != null) {
            writeLoginActivity("Successful login from " + userNameTextField.getText()
                    + " - " +currentTime);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/schedule.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1640, 600);
                stage.setTitle("Scheduler");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                writeLoginActivity("Unsuccessful login from " + userNameTextField.getText()
                        + " - " +currentTime);
            }
        } else {
            loginErrorLabel.setText(rb.getString("error"));
            writeLoginActivity("Unsuccessful login from " + userNameTextField.getText()
            + " - " +currentTime);
        }
    }
    public void languageToEnglishButtonClicked(ActionEvent actionEvent) {
        languageToFrenchButton.setDisable(false);
        languageToEnglishButton.setDisable(true);
        rb = ResourceBundle.getBundle("sample/Lang",Locale.ENGLISH);
        setLanguage();
    }
    public void languageToFrenchButtonClicked(ActionEvent actionEvent) {
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
    public void writeLoginActivity(String textToWrite) throws IOException {
        String filename = "login_activity.txt";
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        outputFile.println(textToWrite);
        outputFile.close();
    }
}
