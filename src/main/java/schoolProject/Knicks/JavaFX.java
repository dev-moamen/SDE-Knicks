package schoolProject.Knicks;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFX extends Application{
    private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException, SQLException {
        scene = new Scene(loadFXML("/javafxml/IntroductionScreen"), 800, 800);
        scene.getStylesheets().add(
                JavaFX.class.getResource("/javafxml/app.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Parkify");
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        stage.show();
    }
	public static void setRoot(String fxml) throws IOException {
	    scene.setRoot(loadFXML(fxml));
	}

 private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(JavaFX.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
