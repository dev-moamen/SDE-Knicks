package schoolProject;

import schoolProject.Models.*;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    // Hide logging info from console for now - investigate later, comment the options in pom
    public static void hideConsoleInfo() {
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        Logger.getLogger("org.jboss.logging").setLevel(Level.WARNING);
    }

    public static void main(String[] args) throws SQLException{

        App.hideConsoleInfo();

        H2DB.initializeDB();

        CustomerManager.setDAO(new CustomerJPA());
        CustomerManager.manageCustomers();
        
        JavaFX.launch(JavaFX.class, args);
    }
}
