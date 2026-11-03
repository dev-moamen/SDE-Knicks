module schoolProject {
    requires java.persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires org.hibernate.orm.core;
    requires net.bytebuddy; 

    opens schoolProject to javafx.fxml;
    exports schoolProject;

    opens schoolProject.Controllers to javafx.fxml;
    exports schoolProject.Controllers;

    opens schoolProject.Models to org.hibernate.orm.core, java.persistence;
    exports schoolProject.Models;
}