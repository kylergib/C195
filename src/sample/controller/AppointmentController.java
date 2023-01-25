package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.ContactQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.UserQuery;
import sample.model.Appointment;
import sample.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    public static String appointmentTitleVar;
    public static Appointment currentAppointment;
    public ComboBox appointmentContact;
    public TextField appointmentTitle;
    public TextField appointmentDescription;
    public TextField appointmentLocation;
    public DatePicker appointmentDate;
    public TextField appointmentStartTime;
    public TextField appointmentEndTime;
    public ComboBox appointmentStartCombo;
    public ComboBox appointmentEndCombo;
    public ComboBox customerNameCombo;
    public TextField appointmentType;
    public Label appointmentHeader;
    public Label errorLabel;
    public TextField appointmentId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> comboBoxTimes = FXCollections.observableArrayList();
        comboBoxTimes.add("AM");
        comboBoxTimes.add("PM");
        appointmentHeader.setText((String) appointmentTitleVar);
        appointmentStartCombo.setItems(comboBoxTimes);
        appointmentEndCombo.setItems(comboBoxTimes);

        try {
            appointmentContact.setItems(ContactQuery.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ObservableList<String> customerNames = FXCollections.observableArrayList();
            ObservableList<Customer> allCustomers = CustomerQuery.getAllCustomers();
            for (int i = 0; i < allCustomers.size(); i++) {
                customerNames.add(allCustomers.get(i).getCustomerName());
            }
            customerNameCombo.setItems(customerNames);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (appointmentTitleVar == "Modify Appointment") {

            try {
                appointmentContact.setValue(ContactQuery.getContactName(currentAppointment.getContactId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appointmentTitle.setText(currentAppointment.getTitle());
            appointmentDescription.setText(currentAppointment.getDescription());
            appointmentLocation.setText(currentAppointment.getLocation());
            LocalDate appointmentDateCurrent = currentAppointment.getStart().toLocalDateTime().toLocalDate();

            appointmentDate.setValue(appointmentDateCurrent);
            int startTimeHour = currentAppointment.getStart().toLocalDateTime().getHour();
            String startTimeMinute = String.valueOf(currentAppointment.getStart().toLocalDateTime().getMinute());
            int endTimeHour = currentAppointment.getEnd().toLocalDateTime().getHour();
            String endTimeMinute = String.valueOf(currentAppointment.getEnd().toLocalDateTime().getMinute());
            if (startTimeMinute.length() == 1) {
                startTimeMinute = startTimeMinute + "0";
            }
            if (endTimeMinute.length() == 1) {
                endTimeMinute = endTimeMinute + "0";
            }
            String startCurrentTimeType;
            String endCurrentTimeType;
            if (startTimeHour > 12) {
                startCurrentTimeType = "PM";
                startTimeHour = startTimeHour - 12;
            } else {
                startCurrentTimeType = "AM";
            }

            if (endTimeHour > 12) {
                endCurrentTimeType = "PM";
                endTimeHour = endTimeHour - 12;
            } else {
                endCurrentTimeType = "AM";
            }

            String startTime = String.valueOf(startTimeHour) + ":" + startTimeMinute;
            String endTime = String.valueOf(endTimeHour) + ":" + endTimeMinute;

            System.out.println(startTime + " " + endTime);

            appointmentStartTime.setText(startTime);
            appointmentEndTime.setText(endTime);
            appointmentStartCombo.setValue(startCurrentTimeType);
            appointmentEndCombo.setValue(endCurrentTimeType);
            try {
                customerNameCombo.setValue(CustomerQuery.getCustomerName(currentAppointment.getCustomerId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appointmentType.setText(currentAppointment.getType());
            appointmentId.setText(String.valueOf(currentAppointment.getId()));

        } else {
            appointmentEndCombo.setValue("AM");
            appointmentStartCombo.setValue("AM");
        }
    }

    public void appointmentSaveClicked(ActionEvent actionEvent) throws SQLException, IOException {
        String contact = (String) appointmentContact.getValue();
        String name = (String) customerNameCombo.getValue();
        String title = appointmentTitle.getText();
        String description = appointmentDescription.getText();
        String location = appointmentLocation.getText();
        LocalDate dateAppointment = appointmentDate.getValue();
        String startTime = appointmentStartTime.getText();
        String endTime = appointmentEndTime.getText();
        String startCombo = (String) appointmentStartCombo.getValue();
        String endCombo = (String) appointmentEndCombo.getValue();
        String appointmentTypeNew = appointmentType.getText();
        System.out.println(startTime + " " + endTime);

        System.out.println("CONTACT" + contact);
        if (contact == null) {
            errorLabel.setText("Contact field cannot be blank");
            return;
        }
        if (name == null) {
            errorLabel.setText("Name field cannot be blank");
            return;
        }
        if (title == "") {
            errorLabel.setText("Title field cannot be blank");
            return;
        }
        if (description == "") {
            errorLabel.setText("Description field cannot be blank");
            return;
        }
        if (location == "") {
            errorLabel.setText("Location field cannot be blank");
            return;
        }
        if (dateAppointment == null) {
            errorLabel.setText("Date field cannot be blank");
            return;
        }
        if (startTime == "") {
            errorLabel.setText("Start time field cannot be blank");
            return;
        }
        if (endTime == "") {
            errorLabel.setText("End time field cannot be blank");
            return;
        }
        if (appointmentTypeNew == "") {
            errorLabel.setText("Type field cannot be blank");
            return;
        }
//        System.out.println("TESTING SPLIT " + startTime.split(":")[0]);
        int startTimeHour = -1;
        int startTimeMinute = 0;
        try {
            startTimeHour = Integer.valueOf(startTime.split(":")[0]);
            startTimeMinute = Integer.valueOf(startTime.split(":")[1]);
        } catch (Exception e) {
            System.out.println("Only 1 number");
//            startTime = startTime + ":00" + ":00";
        }

        if (startCombo == "PM" && startTimeHour < 12 && startTimeHour > 0 ) {
            int newTime = startTimeHour + 12;
            startTime = String.valueOf(newTime + ":" + startTimeMinute + ":00");
        } else if (startCombo == "AM" && startTimeHour == 12) {
            int newTime = 0;
            startTime = String.valueOf(newTime + ":" + startTimeMinute + ":00");
        } else if (startTime.contains(":")) {
            startTime = startTime + ":00";
        } else {
            startTime = startTime + ":00" + ":00";
        }


        int endTimeHour = -1;
        int endTimeMinute = 0;
        try {
            endTimeHour = Integer.valueOf(endTime.split(":")[0]);
            endTimeMinute = Integer.valueOf(endTime.split(":")[1]);
        } catch (Exception e) {
            System.out.println("Only one number");
//            endTime = endTime + ":00" + ":00";
        }
            if (endCombo == "PM" && endTimeHour < 12 && endTimeHour > 0 ) {
                int newTime = endTimeHour + 12;
                endTime = String.valueOf(newTime + ":" + endTimeMinute + ":00");
            } else if (endCombo == "AM" && endTimeHour == 12) {
                int newTime = 0;
                endTime = String.valueOf(newTime + ":" + endTimeMinute + ":00");
            } else if (endTime.contains(":")) {
                endTime = endTime + ":00";
            } else {
                endTime = endTime + ":00" + ":00";
            }
            System.out.println("START TIME "+startTime +" "+startCombo);




        String finalStart = String.valueOf(dateAppointment) + " " + startTime;
        System.out.println("END TIME "+endTime+" "+ endCombo);
        String finalEnd = String.valueOf(dateAppointment) + " " + endTime;
        System.out.println(finalStart);
        System.out.println(finalEnd);
        Timestamp appointmentStart;
        Timestamp appointmentEnd;
        try {
            appointmentStart = Timestamp.valueOf(finalStart);
            appointmentEnd = Timestamp.valueOf(finalEnd);
            System.out.println(appointmentStart.compareTo(appointmentEnd));
        } catch (Exception e) {
            errorLabel.setText("Start and end times cannot have letters in the text field.");
            return;

        }
            if (appointmentStart.compareTo(appointmentEnd) >= 0) {
                errorLabel.setText("End time is before start time");
                return;
            } else {
                errorLabel.setText("");
            }
            System.out.println(appointmentStart + " " + appointmentEnd);
            if (appointmentStart.toLocalDateTime().getHour() < 8 || appointmentStart.toLocalDateTime().getHour() > 21) {
                errorLabel.setText("Appointment has to be within store hours. 8:00 AM and 10:00 PM (22:00)");
                return;
            } else if (appointmentEnd.toLocalDateTime().getHour() < 8 || appointmentEnd.toLocalDateTime().getHour() > 22) {
                errorLabel.setText("Appointment has to be within store hours. 8:00 AM and 10:00 PM (22:00)");
                return;
            } else if (appointmentEnd.toLocalDateTime().getHour() == 22 && appointmentEnd.toLocalDateTime().getMinute() > 0) {
                errorLabel.setText("Appointment has to be within store hours. 8:00 AM and 10:00 PM (22:00)");
                return;
            } else if (AppointmentQuery.checkConflictingAppointments(customerNameCombo.getSelectionModel().getSelectedIndex()+1,appointmentStart) ||
                    AppointmentQuery.checkConflictingAppointments(customerNameCombo.getSelectionModel().getSelectedIndex()+1,appointmentEnd)) {
                errorLabel.setText("Customer already has an appointment between this time.");
                return;
            }
            if (appointmentTitleVar == "Add Appointment") {
                Appointment newAppointment = new Appointment(AppointmentQuery.getAllAppointments().size()+1,
                        title,description,location,appointmentTypeNew,appointmentStart,appointmentEnd,
                        customerNameCombo.getSelectionModel().getSelectedIndex()+1, Main.myUser.getUserId(),
                        appointmentContact.getSelectionModel().getSelectedIndex()+1);
                System.out.println(newAppointment.getContactId() + " " + newAppointment.getCustomerId());
                int rowsAffected = AppointmentQuery.insert(newAppointment);
                System.out.println(rowsAffected);
            } else if (appointmentTitleVar == "Modify Appointment") {
                Appointment newAppointment = new Appointment(currentAppointment.getId(),
                        title,description,location,appointmentTypeNew,appointmentStart,appointmentEnd,
                        customerNameCombo.getSelectionModel().getSelectedIndex()+1, Main.myUser.getUserId(),
                        appointmentContact.getSelectionModel().getSelectedIndex()+1);
                System.out.println(newAppointment.getContactId() + " " + newAppointment.getCustomerId());
                int rowsAffected = AppointmentQuery.update(newAppointment);
                System.out.println(rowsAffected);



            }
            appointmentCancelClicked(actionEvent);
//            System.out.println("Saved successfully");




//        public Appointment(int id, String title, String description, String location,
//                String type, Timestamp start, Timestamp end, int customerId,
//        int userId, int contactId)

    }

    public void appointmentCancelClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedule.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1550, 600);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
    }

}
