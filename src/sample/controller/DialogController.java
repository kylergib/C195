package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
/**
 *
 * @author Kyle Gibson
 */
public class DialogController  implements Initializable {
    public static String newLabelText;
    public static boolean confirmVar;
    public static String dialogType;
    public Label messageLabel;
    public Button dialogCancelButton;
    public Button dialogConfirmButton;

    /**
     * code that runs on startup of the stage/class
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText(newLabelText);
        if (dialogType == "fifteen") {
            dialogCancelButton.setVisible(false);
        }
    }
    /**
     * @param actionEvent the button click is pressed and triggers a confirmation in the dialog
     */
    public void dialogConfirmButtonClicked(ActionEvent actionEvent) {
        confirmVar = true;
        Stage stage = (Stage) dialogConfirmButton.getScene().getWindow();
        stage.close();
    }
    /**
     * @param actionEvent the button click is pressed and triggers a cancel in the dialog
     */
    public void dialogCancelButtonClicked(ActionEvent actionEvent) {
        confirmVar = false;
        Stage stage = (Stage) dialogConfirmButton.getScene().getWindow();
        stage.close();
    }
}
