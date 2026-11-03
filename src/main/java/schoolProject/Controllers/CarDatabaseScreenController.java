package schoolProject.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.JavaFX;

// NEW: Controller for returning from the car database to the Admin dashboard.
public class CarDatabaseScreenController {

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("AdminScreen");
    }
}
