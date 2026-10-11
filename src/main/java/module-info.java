module schoolProject {
    requires javafx.controls;
    requires javafx.fxml;

    opens schoolProject to javafx.fxml;
    exports schoolProject;
}