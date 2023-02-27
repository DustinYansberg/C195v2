package com.example.c195v1;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPageController implements Initializable {
    public TextArea displayMessage;
    public Button okayButton;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayMessage.setText(Main.errorMessage.getErrorMessage());

        if(Main.FRENCH){
            errorLabel.setText("Erreur");
            okayButton.setText("Confirmer");
        }
    }

    public void onOkayClick(ActionEvent actionEvent) {
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }
}
