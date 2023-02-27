package com.example.c195v1;

import Model.Appointment;
import helper.AppointmentAlert;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentAlertController implements Initializable {
    public Label appIdLabel;
    public Button confirmButton;
    public Label welcomeMessage;
    public Label appDateTimeLabel;

    Appointment appointment = AppointmentAlert.getAppointment();


    public void onOkButtonPress(ActionEvent actionEvent) {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeMessage.setText(AppointmentAlert.getAlert());
        if (welcomeMessage.getText() == "Welcome, this appoint will begin within fifteen minutes:"){
            appIdLabel.setText("Appointment ID:  " + appointment.getAppointmentID());
            appDateTimeLabel.setText("Appointment Date and Time: " + appointment.getStart());
        }
        else{
            appIdLabel.setText("");
            appDateTimeLabel.setText("");
        }


    }


}
