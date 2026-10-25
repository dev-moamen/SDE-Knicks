package schoolProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class IntroductionController {

    @FXML
    private Button getStarted;

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        getStarted.getScene().setRoot(root);
    }

}