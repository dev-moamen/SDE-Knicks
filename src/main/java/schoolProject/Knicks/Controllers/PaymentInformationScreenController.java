package schoolProject.Knicks.Controllers;

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
import schoolProject.Knicks.JavaFX;
import schoolProject.Knicks.Models.Customer;
import schoolProject.Knicks.Models.CurrentUser;
import schoolProject.Knicks.Models.Payment;
import schoolProject.Knicks.Models.PaymentManager;
import schoolProject.Knicks.Models.PaymentRepo;

public class PaymentInformationScreenController {

    @FXML private TextField cardNumberLoader;
    @FXML private TextField expirationLoader;
    @FXML private TextField cvvLoader;
    @FXML private TextField nameLoader;

    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Long> cardNumberEntry;
    @FXML private TableColumn<Payment, Integer> monthEntry;
    @FXML private TableColumn<Payment, Integer> yearEntry;
    @FXML private TableColumn<Payment, Integer> cvvEntry;
    @FXML private TableColumn<Payment, String> nameEntry;

    private final ObservableList<Payment> payments =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cardNumberEntry.setCellValueFactory(
                new PropertyValueFactory<>("number"));
        monthEntry.setCellValueFactory(
                new PropertyValueFactory<>("month"));
        yearEntry.setCellValueFactory(
                new PropertyValueFactory<>("year"));
        cvvEntry.setCellValueFactory(
                new PropertyValueFactory<>("cvv"));
        nameEntry.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        if (paymentTable != null) {
            paymentTable.setItems(payments);
        }

        loadPayments();
    }

    private void loadPayments() {
        Customer customer = JavaFX.getBean(CurrentUser.class).getCustomer();
        if (customer == null) {
            return;
        }
        payments.setAll(JavaFX.getBean(PaymentRepo.class).findByCustomerId(customer.getId()));
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/MyProfileScreen");
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

        Customer customer = JavaFX.getBean(CurrentUser.class).getCustomer();
        if (customer == null) {
            showAlert("No signed-in customer.");
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

        long cardNumberValue;
        try {
            cardNumberValue = Long.parseLong(cardNumber);
        } catch (NumberFormatException e) {
            showAlert("Card number is too large.");
            return;
        }

        String[] expirationParts = expiration.split("/");
        int month = Integer.parseInt(expirationParts[0]);
        int year = Integer.parseInt(expirationParts[1]);
        if (expirationParts[1].length() == 2) {
            year += 2000;
        }

        Payment payment = Payment.builder()
                .number(cardNumberValue)
                .name(name)
                .month(month)
                .year(year)
                .cvv(Integer.parseInt(cvv))
                .customer(customer)
                .build();

        JavaFX.getBean(PaymentManager.class).savePayment(payment);
        loadPayments();

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
}
