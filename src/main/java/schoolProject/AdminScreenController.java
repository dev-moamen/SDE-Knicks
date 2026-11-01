package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminScreenController {

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        App.setRoot("IntroductionScreen");
    }

    @FXML
    void switchToScanPlateScreen(ActionEvent event) throws IOException {
        App.setRoot("ScanPlateScreen");
    }

    @FXML
    void switchToCarDatabaseScreen(ActionEvent event) throws IOException {
        App.setRoot("CarDatabaseScreen");
    }

    @FXML
    void switchToPaymentDatabaseScreen(ActionEvent event) throws IOException {
        App.setRoot("PaymentDatabaseScreen");
    }

}