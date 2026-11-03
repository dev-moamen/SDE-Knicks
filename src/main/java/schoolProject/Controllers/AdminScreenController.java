package schoolProject.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.JavaFX;

public class AdminScreenController {

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("IntroductionScreen");
    }

    @FXML
    void switchToScanPlateScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("ScanPlateScreen");
    }

    @FXML
    void switchToCarDatabaseScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("CarDatabaseScreen");
    }

    @FXML
    void switchToPaymentDatabaseScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("PaymentDatabaseScreen");
    }
}
