module com.project.three.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jfr;
    requires java.sql;


    opens com.project.three.demo to javafx.fxml;
    exports com.project.three.demo;
}