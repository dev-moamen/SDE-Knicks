package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PaymentDatabaseScreenController {
    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }
}