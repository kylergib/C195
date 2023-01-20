package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Schedule {
//    private ObservableList<Customer> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }
    public ObservableList allAppointments() {
        return allAppointments;
    }

}
