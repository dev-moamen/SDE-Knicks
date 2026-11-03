package schoolProject.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import schoolProject.JavaFX;

public class IntroductionScreenController {

    @FXML
    private Button getStarted;

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("HomeScreen");
    }

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("AdminScreen");
    }

}