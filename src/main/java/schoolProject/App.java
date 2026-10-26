package schoolProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.h2.tools.Server;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    private static Scene scene;
    private Server h2WebServer;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Start the H2 web console
            h2WebServer = Server.createWebServer(
                    "-web",
                    "-webAllowOthers",
                    "-webPort", "8082"
            ).start();

            System.out.println("H2 Console: http://localhost:8082");

            // Initialize the database
            Database.createTable();

        } catch (SQLException e) {
            throw new RuntimeException("Could not start H2 Console", e);
        }

        scene = new Scene(loadFXML("IntroductionScreen"), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Parkify");
        stage.show();
    }

    @Override
    public void stop() {
        if (h2WebServer != null) {
            h2WebServer.stop();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
