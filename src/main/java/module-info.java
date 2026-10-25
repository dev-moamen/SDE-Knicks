module schoolProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;

    opens schoolProject to javafx.fxml;
    exports schoolProject;
}