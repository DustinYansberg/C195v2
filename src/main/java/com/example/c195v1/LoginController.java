package com.example.c195v1;

import DAO.UserDAO;
import helper.ActiveUser;
import helper.LogInAttempt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import Model.User;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button logInButton;
    public Label userZoneId;
    public Label passwordLabel;
    public Label usernameLabel;
    @FXML
    private TextField userNameInput;
    @FXML
    private TextField passwordInput;
    static ActiveUser activeUser = new ActiveUser(new User(0, "default"));

    @FXML
    protected void onLogInButtonClick() throws SQLException, IOException {
        String username = userNameInput.getText();
        String password = passwordInput.getText();
        boolean successfulLogin = false;

        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(username);

        activeUser.setActiveUser(user);
//        activeUser.logInAttemptToLog();
        if (user != null){
            int usernameID = user.userName(username);
            int passwordID = user.password(password);

//        conditions for forbidding access
            if (usernameID == 0 || passwordID != usernameID){
                loginFailMessage();

            }else{
//            allow access, bring to main page
                successfulLogin = true;
                Parent part = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-page-view.fxml")));
                Stage mainPage = new Stage();
                mainPage.setTitle("Main Page");
                mainPage.setScene(new Scene(part));
                mainPage.show();


                Stage stage = (Stage) logInButton.getScene().getWindow();
                stage.close();
            }

        }else{
            loginFailMessage();
        }

        LogInAttempt.log(user, successfulLogin);

    }

    private void loginFailMessage() throws IOException {
        if(Main.ENGLISH){
            Main.errorMessage.setErrorMessage("Login failed");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("error-view.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(parent));
            stage.show();
        } else if (Main.FRENCH) {
            Main.errorMessage.setErrorMessage("échec de la connexion");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("error-view.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Erreur");
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId systemZoneId = ZoneId.systemDefault();
        if (Main.ENGLISH){
            userZoneId.setText("Zone ID " + systemZoneId);
        }
        if(Main.FRENCH){
            userZoneId.setText("Zone ID " + systemZoneId);
            usernameLabel.setText("nom d'utilisateur");
            passwordLabel.setText("mot de passe");
        }

    }
}