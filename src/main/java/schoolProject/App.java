package schoolProject;

import schoolProject.Models.*;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException{

        H2DB.initializeDB();

        CustomerManager.setDAO(new CustomerJPA());
        CustomerManager.manageCustomers();
        
        JavaFX.launch(JavaFX.class, args);
    }
}
