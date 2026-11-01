package schoolProject;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class ScanPlateScreenController {

    @FXML
    private Button scanPlateBack;

    @FXML
    private Button uploadFile;

    // the image the user picked
    private File selectedImage;

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }

    @FXML
    void uploadLicensePlateFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose an image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        selectedImage = chooser.showOpenDialog(uploadFile.getScene().getWindow());
    }
}