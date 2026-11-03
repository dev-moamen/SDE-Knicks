package schoolProject;

import java.sql.SQLException;
import org.h2.tools.Server;

public class H2DB {

    private static void startDatabase() throws SQLException {
        new Server().runTool("-tcp", "-web", "-ifNotExists");
    }

    public static void initializeDB() throws SQLException{
        startDatabase();

        System.out.println("Open your browser and navigate to http://localhost:8082/");
        System.out.println("JDBC URL: jdbc:h2:file:~/h2_db/parkify_db");
        System.out.println("User Name: sa");
        System.out.println("Password: ");
    }
}
