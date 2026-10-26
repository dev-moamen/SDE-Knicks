package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MyProfileScreenController {

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        App.setRoot("HomeScreen");
    }

    @FXML
    void switchToMyCarsScreen(ActionEvent event) throws IOException {
        App.setRoot("MyCarsScreen");
    }

    @FXML
    void switchToPaymentInformationScreen(ActionEvent event) throws IOException {
        App.setRoot("PaymentInformationScreen");
    }
}