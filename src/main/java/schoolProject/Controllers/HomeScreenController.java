package schoolProject.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.JavaFX;

public class HomeScreenController {

    @FXML
    void switchToMyReservationsScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("MyReservationsScreen");
    }

    @FXML
    void switchToParkingLotScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("ParkingLotScreen");
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("MyProfileScreen");
    }

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("IntroductionScreen");
    }
}