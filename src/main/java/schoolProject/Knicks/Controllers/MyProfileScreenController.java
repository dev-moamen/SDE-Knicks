package schoolProject.Knicks.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import schoolProject.Knicks.JavaFX;

public class MyProfileScreenController {

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/HomeScreen");
    }

    @FXML
    void switchToMyCarsScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/MyCarsScreen");
    }

    @FXML
    void switchToPaymentInformationScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/PaymentInformationScreen");
    }
}