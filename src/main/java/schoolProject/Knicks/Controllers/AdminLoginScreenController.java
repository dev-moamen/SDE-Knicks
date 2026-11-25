package schoolProject.Knicks.Controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import schoolProject.Knicks.JavaFX;
import schoolProject.Knicks.Models.AdminManager;

public class AdminLoginScreenController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Label hintLabel;
    @FXML private Button signInButton;

    @FXML
    public void initialize() {
        hintLabel.setText("Demo account \u2014 " + AdminManager.DEFAULT_USERNAME
                + " / " + AdminManager.DEFAULT_PASSWORD);
        usernameField.textProperty().addListener((observable, oldText, text) -> hideError());
        passwordField.textProperty().addListener((observable, oldText, text) -> hideError());
        Platform.runLater(usernameField::requestFocus);
    }

    @FXML
    void signIn(ActionEvent event) {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        if (!JavaFX.getBean(AdminManager.class).authenticate(username, password)) {
            showError("Wrong username or password.");
            passwordField.clear();
            passwordField.requestFocus();
            return;
        }

        try {
            JavaFX.setRoot("/javafxml/AdminScreen");
        } catch (IOException exception) {
            showError("Could not open the dashboard. Please try again.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideError() {
        if (errorLabel.isVisible()) {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }
    }

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/IntroductionScreen");
    }
}
