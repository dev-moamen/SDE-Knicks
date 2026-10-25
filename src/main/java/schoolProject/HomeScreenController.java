package schoolProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeScreenController {

    @FXML
    private Button myReservations;

    @FXML
    private Button parkingLot;

    @FXML
    private Button settings;

    @FXML
    void switchToMyReservationsScreen(ActionEvent event) throws IOException {
        App.setRoot("MyReservations");
    }

    @FXML
    void switchToParkingLotScreen(ActionEvent event) throws IOException {
        App.setRoot("ParkingLot");
    }

    @FXML
    void switchToSettingsScreen(ActionEvent event) throws IOException {
        App.setRoot("Settings");
    }

}