package schoolProject.Knicks.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.Knicks.JavaFX;

public class AdminScreenController {

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/IntroductionScreen");
    }

    @FXML
    void switchToScanPlateScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/ScanPlateScreen");
    }

    @FXML
    void switchToCarDatabaseScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/CarDatabaseScreen");
    }

    @FXML
    void switchToPaymentDatabaseScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/PaymentDatabaseScreen");
    }
}
