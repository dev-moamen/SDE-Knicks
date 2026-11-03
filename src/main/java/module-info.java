module schoolProject {
    requires java.persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;

    opens schoolProject to javafx.fxml, org.hibernate.orm.core;
    exports schoolProject;

    opens schoolProject.Controllers to javafx.fxml;
    exports schoolProject.Controllers;
}