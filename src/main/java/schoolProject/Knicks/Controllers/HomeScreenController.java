package schoolProject.Knicks.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.Knicks.JavaFX;

public class HomeScreenController {

    @FXML
    void switchToMyReservationsScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/MyReservationsScreen");
    }

    @FXML
    void switchToParkingLotScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/ParkingLotScreen");
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/MyProfileScreen");
    }

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/IntroductionScreen");
    }
}