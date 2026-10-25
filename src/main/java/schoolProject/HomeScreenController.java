package schoolProject;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomeScreenController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}