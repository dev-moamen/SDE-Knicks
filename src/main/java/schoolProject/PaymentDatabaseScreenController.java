package schoolProject;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

// NEW: Controller for returning from the payment database to the Admin dashboard.
public class PaymentDatabaseScreenController {

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }
}
