package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class CarDatabaseScreenController {
    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }
}