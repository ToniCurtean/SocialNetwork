module com.example.socialnetworkchatfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.socialnetworkchatfx to javafx.fxml;
    exports com.example.socialnetworkchatfx;
    opens com.example.socialnetworkchatfx.controller to javafx.fxml;
    exports com.example.socialnetworkchatfx.controller;
}