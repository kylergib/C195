package sample.model;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static sample.Main.rb;

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

    public void loginButtonClicked(ActionEvent actionEvent) {
        System.out.println("Login button clicked");
        System.out.println(System.getProperty("user.language"));

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
    }
}
