package schoolProject.Knicks.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.Knicks.JavaFX;

// NEW: Controller for returning from the car database to the Admin dashboard.
public class CarDatabaseScreenController {

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/AdminScreen");
    }
}
