package schoolProject;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

// NEW: Provides the file picker for selecting a license plate image.
public class ScanPlateScreenController {

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }

    @FXML
    void uploadLicensePlateFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select License Plate Image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".bmp")
        );

        Window window = ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = chooser.showOpenDialog(window);

        if (selectedFile != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Image Selected");
            alert.setHeaderText("License plate image selected");
            alert.setContentText("Selected file: " + selectedFile.getName()
                    + "\nAutomatic plate recognition is not connected yet.");
            alert.showAndWait();
        }
    }
}
