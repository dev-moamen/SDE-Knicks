package schoolProject.Knicks.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.Knicks.JavaFX;

public class MyReservationsScreenController {

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/HomeScreen");
    }
}
