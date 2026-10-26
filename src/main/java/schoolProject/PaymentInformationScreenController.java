package schoolProject;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PaymentInformationScreenController {

    @FXML private TextField cardNumberLoader;
    @FXML private TextField expirationLoader;
    @FXML private TextField cvvLoader;
    @FXML private TextField nameLoader;

    @FXML private TableView<PaymentInformation> paymentTable;
    @FXML private TableColumn<PaymentInformation, String> cardNumberEntry;
    @FXML private TableColumn<PaymentInformation, String> expirationEntry;
    @FXML private TableColumn<PaymentInformation, String> cvvEntry;
    @FXML private TableColumn<PaymentInformation, String> nameEntry;

    private final ObservableList<PaymentInformation> payments =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cardNumberEntry.setCellValueFactory(
                new PropertyValueFactory<>("cardNumber"));
        expirationEntry.setCellValueFactory(
                new PropertyValueFactory<>("expiration"));
        cvvEntry.setCellValueFactory(
                new PropertyValueFactory<>("cvv"));
        nameEntry.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        if (paymentTable != null) {
            paymentTable.setItems(payments);
        }
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        App.setRoot("MyProfileScreen");
    }

    @FXML
    void loadPaymentInformationInputs(ActionEvent event) {
        String cardNumber = cardNumberLoader.getText().trim();
        String expiration = expirationLoader.getText().trim();
        String cvv = cvvLoader.getText().trim();
        String name = nameLoader.getText().trim();

        if (cardNumber.isEmpty() || expiration.isEmpty()
                || cvv.isEmpty() || name.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        if (!cardNumber.matches("\\d{13,19}")) {
            showAlert("Enter a valid card number.");
            return;
        }

        if (!expiration.matches("(0[1-9]|1[0-2])/\\d{2,4}")) {
            showAlert("Enter expiration as MM/YY or MM/YYYY.");
            return;
        }

        if (!cvv.matches("\\d{3,4}")) {
            showAlert("Enter a valid CVV.");
            return;
        }

        payments.add(new PaymentInformation(
                cardNumber, expiration, cvv, name));

        cardNumberLoader.clear();
        expirationLoader.clear();
        cvvLoader.clear();
        nameLoader.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class PaymentInformation {
        private final String cardNumber;
        private final String expiration;
        private final String cvv;
        private final String name;

        public PaymentInformation(String cardNumber, String expiration,
                                  String cvv, String name) {
            this.cardNumber = cardNumber;
            this.expiration = expiration;
            this.cvv = cvv;
            this.name = name;
        }

        public String getCardNumber() { return cardNumber; }
        public String getExpiration() { return expiration; }
        public String getCvv() { return cvv; }
        public String getName() { return name; }
    }
}