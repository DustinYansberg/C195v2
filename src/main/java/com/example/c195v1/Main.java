package com.example.c195v1;

import helper.ErrorMessage;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;


public class Main extends Application {
    public static ZoneId systemZoneId = ZoneId.systemDefault();
    public static Locale systemLocale = Locale.getDefault();
    public static String systemLanguage = systemLocale.getDisplayLanguage();

    public static boolean ENGLISH = true;
    public static boolean FRENCH = false;


    //    This active user will be passed to each screen and used for tracking the current user's activity it starts
//    empty because we do not know who the activeUser is yet.

    public static ErrorMessage errorMessage = new ErrorMessage("no error message");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 499, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        systemLanguage = "French";

        if (Objects.equals(systemLanguage, "French")){
            ENGLISH = false;
            FRENCH = true;
        }

        launch();
        JDBC.closeConnection();
    }



}