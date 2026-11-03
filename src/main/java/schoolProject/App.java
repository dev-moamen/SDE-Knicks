package schoolProject;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException{

        H2DB.initializeDB();
        JavaFX.launch(JavaFX.class, args);
    }
}
