package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.*;
import sample.model.User;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * JavaDocs folder is located in JavaII/JavaDocs/
 */
public class Main extends Application {
    public static ResourceBundle rb = ResourceBundle.getBundle("sample/Lang",Locale.getDefault());
    public static User myUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBConnection.openConnection();
//        if (!startTime.contains()

        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
