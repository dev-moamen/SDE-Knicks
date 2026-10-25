package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MyReservationsScreenController {

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        App.setRoot("HomeScreen");
    }
}
