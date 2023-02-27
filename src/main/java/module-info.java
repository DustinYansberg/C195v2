module com.example.c195v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;


    opens com.example.c195v1 to javafx.graphics, javafx.fxml, javafx.base;

//    Won't populate anything in the customer tableview without this
    opens Model  to javafx.graphics, javafx.fxml, javafx.base;
    exports com.example.c195v1;
}