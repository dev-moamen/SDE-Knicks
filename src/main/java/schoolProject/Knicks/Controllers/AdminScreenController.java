package schoolProject.Knicks.Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import schoolProject.Knicks.JavaFX;
import schoolProject.Knicks.Models.Car;
import schoolProject.Knicks.Models.CarRepo;
import schoolProject.Knicks.Models.Customer;
import schoolProject.Knicks.Models.CustomerRepo;
import schoolProject.Knicks.Models.Payment;
import schoolProject.Knicks.Models.PaymentRepo;
import schoolProject.Knicks.Models.PriceFormat;
import schoolProject.Knicks.Models.Transaction;
import schoolProject.Knicks.Models.TransactionRepo;

public class AdminScreenController {

    private static final DateTimeFormatter TRANSACTION_TIME =
            DateTimeFormatter.ofPattern("MMM d, HH:mm");

    @FXML private ComboBox<Customer> customerPicker;
    @FXML private Label customerSummary;

    @FXML private TableView<Car> carsTable;
    @FXML private TableColumn<Car, String> licensePlateEntry;
    @FXML private TableColumn<Car, String> brandEntry;
    @FXML private TableColumn<Car, String> modelEntry;
    @FXML private TableColumn<Car, String> colorEntry;
    @FXML private TableColumn<Car, Integer> yearEntry;

    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, String> cardNumberEntry;
    @FXML private TableColumn<Payment, String> nameEntry;
    @FXML private TableColumn<Payment, String> expiryEntry;

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, LocalDateTime> paidEntry;
    @FXML private TableColumn<Transaction, String> spotEntry;
    @FXML private TableColumn<Transaction, BigDecimal> amountEntry;

    private final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private final ObservableList<Car> cars = FXCollections.observableArrayList();
    private final ObservableList<Payment> payments = FXCollections.observableArrayList();
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private FilteredList<Customer> filteredCustomers;

    @FXML
    public void initialize() {
        setupCarsTable();
        setupPaymentTable();
        setupTransactionTable();
        setupCustomerPicker();
    }

    private void setupCarsTable() {
        licensePlateEntry.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        brandEntry.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelEntry.setCellValueFactory(new PropertyValueFactory<>("model"));
        colorEntry.setCellValueFactory(new PropertyValueFactory<>("color"));
        yearEntry.setCellValueFactory(new PropertyValueFactory<>("year"));

        carsTable.setItems(cars);
        carsTable.setPlaceholder(new Label("Select a customer to see their vehicles."));
    }

    private void setupPaymentTable() {
        cardNumberEntry.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(maskCardNumber(cell.getValue().getNumber())));
        nameEntry.setCellValueFactory(new PropertyValueFactory<>("name"));
        expiryEntry.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                String.format("%02d/%02d", cell.getValue().getMonth(), cell.getValue().getYear() % 100)));

        paymentTable.setItems(payments);
        paymentTable.setPlaceholder(new Label("Select a customer to see their payment methods."));
    }

    private void setupTransactionTable() {
        paidEntry.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getPaidAt()));
        spotEntry.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getReservation() == null
                        || cell.getValue().getReservation().getSpot() == null
                        ? ""
                        : cell.getValue().getReservation().getSpot().getCode()));
        amountEntry.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getAmount()));

        paidEntry.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : TRANSACTION_TIME.format(value));
            }
        });
        amountEntry.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : PriceFormat.euro(value));
            }
        });

        transactionTable.setItems(transactions);
        transactionTable.setPlaceholder(new Label("Select a customer to see their transactions."));
    }

    private void setupCustomerPicker() {
        allCustomers.setAll(JavaFX.getBean(CustomerRepo.class).findAll());
        filteredCustomers = new FilteredList<>(allCustomers, customer -> true);

        customerPicker.setItems(filteredCustomers);
        customerPicker.setEditable(true);
        customerPicker.setVisibleRowCount(8);
        customerPicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(Customer customer) {
                return customer == null ? "" : displayName(customer);
            }

            @Override
            public Customer fromString(String text) {
                if (text == null || text.isBlank()) {
                    return null;
                }
                String query = text.trim();
                return filteredCustomers.stream()
                        .filter(customer -> displayName(customer).equalsIgnoreCase(query))
                        .findFirst()
                        .orElseGet(() -> filteredCustomers.isEmpty() ? null : filteredCustomers.get(0));
            }
        });
        customerPicker.setCellFactory(list -> customerCell());
        customerPicker.setButtonCell(customerCell());

        customerPicker.getEditor().focusedProperty().addListener((observable, wasFocused, focused) -> {
            if (focused) {
                Platform.runLater(() -> customerPicker.getEditor().selectAll());
            }
        });

        customerPicker.getEditor().textProperty().addListener((observable, oldText, text) -> {
            if (text == null) {
                return;
            }
            Customer selected = customerPicker.getValue();
            if (selected != null && displayName(selected).equals(text)) {
                return;
            }

            String query = text.trim().toLowerCase(Locale.ROOT);
            filteredCustomers.setPredicate(customer -> query.isEmpty() || matches(customer, query));
            if (!customerPicker.isShowing() && !query.isEmpty()) {
                customerPicker.show();
            }
        });

        customerPicker.valueProperty().addListener((observable, oldCustomer, customer) -> {
            if (customer != null) {
                filteredCustomers.setPredicate(candidate -> true);
            }
            showCustomer(customer);
        });
    }

    private ListCell<Customer> customerCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Customer customer, boolean empty) {
                super.updateItem(customer, empty);
                setText(empty || customer == null ? null : displayName(customer));
            }
        };
    }

    private void showCustomer(Customer customer) {
        if (customer == null) {
            customerSummary.setText("No customer selected");
            cars.clear();
            payments.clear();
            transactions.clear();
            return;
        }

        cars.setAll(JavaFX.getBean(CarRepo.class).findByCustomerId(customer.getId()));
        payments.setAll(JavaFX.getBean(PaymentRepo.class).findByCustomerId(customer.getId()));
        transactions.setAll(JavaFX.getBean(TransactionRepo.class)
                .findByReservationCustomerIdOrderByPaidAtDesc(customer.getId()));

        int vehicleCount = cars.size();
        int paymentCount = payments.size();
        int transactionCount = transactions.size();
        customerSummary.setText("Viewing " + displayName(customer)
                + "  \u00b7  " + vehicleCount + (vehicleCount == 1 ? " vehicle" : " vehicles")
                + "  \u00b7  " + paymentCount
                + (paymentCount == 1 ? " payment method" : " payment methods")
                + "  \u00b7  " + transactionCount
                + (transactionCount == 1 ? " transaction" : " transactions"));
    }

    private String displayName(Customer customer) {
        String name = customer.getName() == null || customer.getName().isBlank()
                ? "Customer #" + customer.getId()
                : customer.getName();
        if (customer.getEmail() != null && !customer.getEmail().isBlank()) {
            return name + " \u00b7 " + customer.getEmail();
        }
        return name + " \u00b7 #" + customer.getId();
    }

    private boolean matches(Customer customer, String query) {
        return contains(customer.getName(), query)
                || contains(customer.getEmail(), query)
                || String.valueOf(customer.getId()).contains(query);
    }

    private boolean contains(String value, String query) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(query);
    }

    private String maskCardNumber(long number) {
        String digits = Long.toString(number);
        String lastFour = digits.length() <= 4 ? digits : digits.substring(digits.length() - 4);
        return "\u2022\u2022\u2022\u2022 " + lastFour;
    }

    @FXML
    void switchToIntroductionScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/IntroductionScreen");
    }

    @FXML
    void switchToScanPlateScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/ScanPlateScreen");
    }
}
