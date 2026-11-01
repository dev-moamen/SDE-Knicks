package schoolProject;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PaymentDatabaseScreenController {

    @FXML
    private Button paymentDatabaseBack;

    @FXML
    private TableView<PaymentInfo> paymentTable;

    @FXML
    private TableColumn<PaymentInfo, String> cardNumberEntry;

    @FXML
    private TableColumn<PaymentInfo, String> expirationEntry;

    @FXML
    private TableColumn<PaymentInfo, String> cvvEntry;

    @FXML
    private TableColumn<PaymentInfo, String> nameEntry;

    // the table's data, empty for now
    private final ObservableList<PaymentInfo> payments = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        cardNumberEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCardNumber()));
        expirationEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getExpiration()));
        cvvEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCvv()));
        nameEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        paymentTable.setPlaceholder(new Label("No payment information yet"));
        paymentTable.setItems(payments);
    }

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }
}