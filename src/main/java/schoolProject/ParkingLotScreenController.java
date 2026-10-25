package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;

public class ParkingLotScreenController {

    @FXML private Rectangle a1;
    @FXML private Rectangle a2;
    @FXML private Rectangle a3;
    @FXML private Rectangle a4;
    @FXML private Rectangle b1;
    @FXML private Rectangle b2;
    @FXML private Rectangle b3;
    @FXML private Rectangle b4;

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        App.setRoot("HomeScreen");
    }

    @FXML
    public void initialize() {
        setupSpot(a1, "A1");
        setupSpot(a2, "A2");
        setupSpot(a3, "A3");
        setupSpot(a4, "A4");
        setupSpot(b1, "B1");
        setupSpot(b2, "B2");
        setupSpot(b3, "B3");
        setupSpot(b4, "B4");
    }

    private void setupSpot(Rectangle spot, String name) {
        spot.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Parking Spot");
            alert.setHeaderText(name);
            alert.setContentText("This parking spot is available.");
            alert.showAndWait();
        });
    }
}
