package schoolProject.Knicks.Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import schoolProject.Knicks.JavaFX;
import schoolProject.Knicks.Models.PriceFormat;
import schoolProject.Knicks.Models.Car;
import schoolProject.Knicks.Models.CarRepo;
import schoolProject.Knicks.Models.CurrentUser;
import schoolProject.Knicks.Models.Customer;
import schoolProject.Knicks.Models.ParkingFloor;
import schoolProject.Knicks.Models.ParkingFloorRepo;
import schoolProject.Knicks.Models.ParkingSpot;
import schoolProject.Knicks.Models.ParkingSpotRepo;
import schoolProject.Knicks.Models.Reservation;
import schoolProject.Knicks.Models.ReservationManager;
import schoolProject.Knicks.Models.ReservationRepo;

public class ParkingLotScreenController {

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MMM d");

    @FXML private Label floorTitle;
    @FXML private Label floorSubtitle;
    @FXML private Label floorStats;
    @FXML private Label priceNote;
    @FXML private HBox floorTabs;
    @FXML private GridPane floorMap;

    private final ToggleGroup floorGroup = new ToggleGroup();
    private final Map<Long, ToggleButton> floorButtons = new HashMap<>();
    private final Map<Long, Reservation> activeBySpot = new HashMap<>();
    private List<ParkingFloor> floors;
    private ParkingFloor selectedFloor;

    @FXML
    public void initialize() {
        floors = JavaFX.getBean(ParkingFloorRepo.class).findAllByOrderByLevelAsc();
        buildFloorTabs();
        if (!floors.isEmpty()) {
            selectFloor(floors.get(0));
        }
    }

    private void buildFloorTabs() {
        for (ParkingFloor floor : floors) {
            ToggleButton tab = new ToggleButton();
            tab.getStyleClass().add("floor-tab");
            tab.setToggleGroup(floorGroup);
            tab.setOnAction(event -> selectFloor(floor));
            floorTabs.getChildren().add(tab);
            floorButtons.put(floor.getId(), tab);
        }
    }

    private void selectFloor(ParkingFloor floor) {
        selectedFloor = floor;
        ToggleButton tab = floorButtons.get(floor.getId());
        if (tab != null) {
            tab.setSelected(true);
        }
        refreshAvailability();
        renderFloor();
    }

    private void refreshAvailability() {
        List<Reservation> active = JavaFX.getBean(ReservationRepo.class)
                .findActiveAt(LocalDateTime.now());
        activeBySpot.clear();
        for (Reservation reservation : active) {
            if (reservation.getSpot() != null) {
                activeBySpot.put(reservation.getSpot().getId(), reservation);
            }
        }
        updateFloorTabs();
    }

    private void updateFloorTabs() {
        ParkingSpotRepo spotRepo = JavaFX.getBean(ParkingSpotRepo.class);
        for (ParkingFloor floor : floors) {
            List<ParkingSpot> spots = spotRepo.findByFloorIdOrderByCodeAsc(floor.getId());
            ToggleButton tab = floorButtons.get(floor.getId());
            if (tab != null) {
                tab.setText(floor.getName() + "  \u00b7  " + countFree(spots) + " free");
            }
        }
    }

    private long countFree(List<ParkingSpot> spots) {
        return spots.stream()
                .filter(ParkingSpot::isAvailable)
                .filter(spot -> !activeBySpot.containsKey(spot.getId()))
                .count();
    }

    private void renderFloor() {
        if (selectedFloor == null) {
            return;
        }

        ParkingSpotRepo spotRepo = JavaFX.getBean(ParkingSpotRepo.class);
        List<ParkingSpot> spots = spotRepo.findByFloorIdOrderByCodeAsc(selectedFloor.getId());

        floorTitle.setText(selectedFloor.getName());
        floorSubtitle.setText(selectedFloor.getDescription());
        floorStats.setText(countFree(spots) + " of " + spots.size() + " free now");

        BigDecimal lowestRate = spots.stream()
                .map(ParkingSpot::getHourlyRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        priceNote.setText("From " + PriceFormat.euro(lowestRate) + "/hour");

        floorMap.getChildren().clear();
        floorMap.getColumnConstraints().clear();
        floorMap.getRowConstraints().clear();

        int leftCount = (spots.size() + 1) / 2;
        int rightCount = spots.size() - leftCount;
        int rows = (Math.max(leftCount, rightCount) + 1) / 2;
        if (rows == 0) {
            return;
        }

        for (int column = 0; column < 5; column++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPrefWidth(column == 2 ? 64 : 142);
            floorMap.getColumnConstraints().add(constraints);
        }
        for (int row = 0; row < rows; row++) {
            RowConstraints constraints = new RowConstraints();
            constraints.setPrefHeight(70);
            floorMap.getRowConstraints().add(constraints);
        }

        for (int index = 0; index < spots.size(); index++) {
            boolean left = index < leftCount;
            int sideIndex = left ? index : index - leftCount;
            int row = sideIndex / 2;
            int column = left ? sideIndex % 2 : sideIndex % 2 + 3;
            floorMap.add(createSpotTile(spots.get(index)), column, row);
        }

        VBox aisle = new VBox();
        aisle.getStyleClass().add("drive-aisle");
        aisle.setAlignment(Pos.CENTER);
        Label aisleLabel = new Label("AISLE");
        aisleLabel.getStyleClass().add("drive-aisle-label");
        aisleLabel.setRotate(-90);
        aisle.getChildren().add(aisleLabel);
        floorMap.add(aisle, 2, 0, 1, rows);
    }

    private Button createSpotTile(ParkingSpot spot) {
        boolean inUse = activeBySpot.containsKey(spot.getId());
        boolean closed = !spot.isAvailable();

        Label code = new Label(spot.getCode());
        code.getStyleClass().add("spot-code");

        Label detail = new Label(spot.getType().getLabel() + "  \u00b7  "
                + PriceFormat.euro(spot.getHourlyRate()) + "/h");
        detail.getStyleClass().add("spot-type");

        Label status = new Label(closed ? "CLOSED" : inUse ? "IN USE" : "FREE");
        status.getStyleClass().add("spot-status");

        VBox content = new VBox(3, code, detail, status);
        content.setAlignment(Pos.CENTER_LEFT);

        Button tile = new Button();
        tile.setGraphic(content);
        tile.setAlignment(Pos.CENTER_LEFT);
        tile.setMaxWidth(Double.MAX_VALUE);
        tile.setMaxHeight(Double.MAX_VALUE);
        tile.getStyleClass().add("spot-tile");
        tile.getStyleClass().add(closed ? "spot-closed" : inUse ? "spot-taken" : "spot-free");
        tile.setAccessibleText(spot.getCode() + ", " + spot.getType().getLabel() + ", "
                + (closed ? "closed" : inUse ? "in use" : "free"));
        tile.setOnAction(event -> handleSpotClick(spot, inUse));
        return tile;
    }

    private void handleSpotClick(ParkingSpot spot, boolean inUse) {
        if (!spot.isAvailable()) {
            showInfo("Spot " + spot.getCode(), "This spot is closed for maintenance.");
            return;
        }
        if (inUse) {
            Reservation reservation = activeBySpot.get(spot.getId());
            String until = "";
            if (reservation != null) {
                LocalDateTime departure = reservation.getDepartureTime();
                String when = departure.toLocalDate().equals(LocalDate.now())
                        ? TIME.format(departure)
                        : DATE.format(departure) + " " + TIME.format(departure);
                until = " until " + when;
            }
            showInfo("Spot " + spot.getCode(), "This spot is in use" + until + ".");
            return;
        }
        openBookingDialog(spot);
    }

    private void openBookingDialog(ParkingSpot spot) {
        Customer customer = JavaFX.getBean(CurrentUser.class).getCustomer();
        if (customer == null) {
            showInfo("No customer", "No signed-in customer is available.");
            return;
        }

        List<Car> cars = JavaFX.getBean(CarRepo.class).findByCustomerId(customer.getId());
        if (cars.isEmpty()) {
            showInfo("No car yet", "Add a car in My Cars before reserving a spot.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Reserve spot");
        dialog.setHeaderText("Reserve spot " + spot.getCode());
        if (floorMap.getScene() != null) {
            dialog.initOwner(floorMap.getScene().getWindow());
        }
        dialog.getDialogPane().getStylesheets().add(
                JavaFX.class.getResource("/javafxml/app.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("booking-dialog");
        dialog.getDialogPane().setPrefWidth(440);

        ButtonType reserveType = new ButtonType("Reserve", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(reserveType, ButtonType.CANCEL);

        ComboBox<Car> carPicker = new ComboBox<>(FXCollections.observableArrayList(cars));
        carPicker.getSelectionModel().selectFirst();
        carPicker.setMaxWidth(Double.MAX_VALUE);
        carPicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(Car car) {
                if (car == null) {
                    return "";
                }
                String details = ((car.getManufacturer() == null ? "" : car.getManufacturer())
                        + " " + (car.getModel() == null ? "" : car.getModel())).trim();
                return car.getLicensePlate() + (details.isEmpty() ? "" : " \u00b7 " + details);
            }

            @Override
            public Car fromString(String text) {
                return null;
            }
        });

        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> startTime = new ComboBox<>(FXCollections.observableArrayList(timeSlots()));
        startTime.setMaxWidth(Double.MAX_VALUE);

        Spinner<Integer> hours = new Spinner<>(1, 12, 2);
        hours.setEditable(true);
        hours.setPrefWidth(90);

        LocalDateTime suggestion = LocalDateTime.now().plusHours(1)
                .withMinute(0).withSecond(0).withNano(0);
        if (suggestion.toLocalTime().isBefore(LocalTime.of(6, 0))
                || suggestion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            suggestion = suggestion.toLocalDate().plusDays(1).atTime(6, 0);
        }
        datePicker.setValue(suggestion.toLocalDate());
        startTime.setValue(String.format("%02d:00", suggestion.getHour()));

        Label spotValue = new Label(spot.getCode() + " \u00b7 " + spot.getFloor().getName()
                + " \u00b7 " + spot.getType().getLabel());
        Label rateValue = new Label(PriceFormat.euro(spot.getHourlyRate()) + " / hour");
        Label total = new Label();
        total.getStyleClass().add("total");
        Runnable updateTotal = () -> {
            Integer duration = readDuration(hours);
            total.setText(duration == null
                    ? "\u2014"
                    : PriceFormat.euro(spot.getHourlyRate()
                            .multiply(BigDecimal.valueOf(duration))));
        };
        updateTotal.run();
        hours.valueProperty().addListener((observable, oldValue, newValue) -> updateTotal.run());
        hours.getEditor().textProperty().addListener(
                (observable, oldValue, newValue) -> updateTotal.run());

        GridPane form = new GridPane();
        form.setHgap(14);
        form.setVgap(10);
        form.setPadding(new Insets(4, 0, 4, 0));
        ColumnConstraints labelColumn = new ColumnConstraints();
        labelColumn.setMinWidth(78);
        ColumnConstraints controlColumn = new ColumnConstraints();
        controlColumn.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().addAll(labelColumn, controlColumn);

        addFormRow(form, 0, "Spot", spotValue);
        addFormRow(form, 1, "Rate", rateValue);
        addFormRow(form, 2, "Car", carPicker);
        addFormRow(form, 3, "Date", datePicker);
        addFormRow(form, 4, "Start", startTime);
        addFormRow(form, 5, "Duration", durationRow(hours));
        addFormRow(form, 6, "Total", total);

        Label hint = new Label("Charged per started hour. The spot must stay free for the whole window.");
        hint.getStyleClass().add("subtitle");
        hint.setWrapText(true);

        dialog.getDialogPane().setContent(new VBox(10, form, hint));

        AtomicReference<Reservation> created = new AtomicReference<>();
        Button reserveButton = (Button) dialog.getDialogPane().lookupButton(reserveType);
        reserveButton.addEventFilter(ActionEvent.ACTION, event -> {
            Car car = carPicker.getValue();
            if (car == null) {
                showInfo("Pick a car", "Choose which car you are parking.");
                event.consume();
                return;
            }
            Integer duration = readDuration(hours);
            if (datePicker.getValue() == null || startTime.getValue() == null
                    || duration == null || duration < 1) {
                showInfo("Pick a time", "Choose a date, start time, and duration.");
                event.consume();
                return;
            }

            LocalDateTime arrival = LocalDateTime.of(datePicker.getValue(),
                    LocalTime.parse(startTime.getValue()));
            if (arrival.isBefore(LocalDateTime.now())) {
                showInfo("Time already passed", "Choose a start time in the future.");
                event.consume();
                return;
            }

            try {
                created.set(JavaFX.getBean(ReservationManager.class)
                        .createReservation(customer, car, spot, arrival, arrival.plusHours(duration)));
            } catch (IllegalArgumentException exception) {
                showInfo("Cannot reserve this spot", exception.getMessage());
                event.consume();
            }
        });

        dialog.showAndWait();
        refreshAvailability();
        renderFloor();

        if (created.get() != null) {
            Reservation reservation = created.get();
            showInfo("Spot " + spot.getCode() + " reserved",
                    DATE.format(reservation.getArrivalTime()) + ", "
                            + TIME.format(reservation.getArrivalTime()) + " \u2013 "
                            + TIME.format(reservation.getDepartureTime())
                            + "\nTotal: " + PriceFormat.euro(reservation.getCost()));
        }
    }

    /** The spinner is editable, so prefer the typed value and fall back to the arrow value. */
    private Integer readDuration(Spinner<Integer> hours) {
        String text = hours.getEditor().getText();
        if (text != null && !text.isBlank()) {
            try {
                int typed = Math.max(1, Math.min(12, Integer.parseInt(text.trim())));
                hours.getValueFactory().setValue(typed);
                return typed;
            } catch (NumberFormatException ignored) {
                // Fall through to the value the arrows set.
            }
        }
        return hours.getValue();
    }

    private List<String> timeSlots() {
        List<String> slots = new ArrayList<>();
        for (int hour = 6; hour <= 22; hour++) {
            slots.add(String.format("%02d:00", hour));
        }
        return slots;
    }

    private void addFormRow(GridPane form, int row, String text, Node control) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        form.add(label, 0, row);
        form.add(control, 1, row);
        GridPane.setHgrow(control, Priority.ALWAYS);
    }

    private HBox durationRow(Spinner<Integer> hours) {
        Label unit = new Label("hours");
        unit.getStyleClass().add("subtitle");
        HBox row = new HBox(8, hours, unit);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private void showInfo(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Parking");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void switchToHomeScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/HomeScreen");
    }
}
