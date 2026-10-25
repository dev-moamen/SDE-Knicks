package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeScreenController {

    @FXML
    void switchToMyReservationsScreen(ActionEvent event) throws IOException {
        App.setRoot("MyReservationsScreen");
    }

    @FXML
    void switchToParkingLotScreen(ActionEvent event) throws IOException {
        App.setRoot("ParkingLotScreen");
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        App.setRoot("MyProfileScreen");
    }

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        App.setRoot("IntroductionScreen");
    }
}