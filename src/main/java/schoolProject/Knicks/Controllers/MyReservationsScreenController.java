package schoolProject.Knicks.Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import schoolProject.Knicks.JavaFX;
import schoolProject.Knicks.Models.PriceFormat;
import schoolProject.Knicks.Models.CurrentUser;
import schoolProject.Knicks.Models.Customer;
import schoolProject.Knicks.Models.Reservation;
import schoolProject.Knicks.Models.ReservationRepo;

public class MyReservationsScreenController {

    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("MMM d, HH:mm");

    @FXML private TableView<Reservation> reservationsTable;
    @FXML private TableColumn<Reservation, LocalDateTime> arrivalTimeEntry;
    @FXML private TableColumn<Reservation, LocalDateTime> departureTimeEntry;
    @FXML private TableColumn<Reservation, String> spotEntry;
    @FXML private TableColumn<Reservation, String> licensePlateEntry;
    @FXML private TableColumn<Reservation, BigDecimal> costEntry;

    private final ObservableList<Reservation> reservations =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        arrivalTimeEntry.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getArrivalTime()));
        departureTimeEntry.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getDepartureTime()));
        spotEntry.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getSpot() == null ? "" : cell.getValue().getSpot().getCode()));
        licensePlateEntry.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getCar() == null ? "" : cell.getValue().getCar().getLicensePlate()));
        costEntry.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getCost()));

        arrivalTimeEntry.setCellFactory(column -> dateCell());
        departureTimeEntry.setCellFactory(column -> dateCell());
        costEntry.setCellFactory(column -> costCell());

        reservationsTable.setItems(reservations);
        reservationsTable.setPlaceholder(new Label("No reservations yet."));
        loadReservations();
    }

    private void loadReservations() {
        Customer customer = JavaFX.getBean(CurrentUser.class).getCustomer();
        if (customer == null) {
            return;
        }
        reservations.setAll(JavaFX.getBean(ReservationRepo.class)
                .findByCustomerIdOrderByArrivalTimeDesc(customer.getId()));
    }

    private TableCell<Reservation, LocalDateTime> dateCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : DATE_TIME.format(value));
            }
        };
    }

    private TableCell<Reservation, BigDecimal> costCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : PriceFormat.euro(value));
            }
        };
    }

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/HomeScreen");
    }
}
