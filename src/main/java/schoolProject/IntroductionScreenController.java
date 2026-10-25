package schoolProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class IntroductionScreenController {

    @FXML
    private Button getStarted;

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        App.setRoot("HomeScreen");
    }

}