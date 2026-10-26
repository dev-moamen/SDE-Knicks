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

public class MyCarsScreenController {

    @FXML private TextField licensePlateLoader;
    @FXML private TextField brandLoader;
    @FXML private TextField modelLoader;
    @FXML private TextField colorLoader;

    @FXML private TableView<Car> carsTable;
    @FXML private TableColumn<Car, String> licensePlateEntry;
    @FXML private TableColumn<Car, String> brandEntry;
    @FXML private TableColumn<Car, String> modelEntry;
    @FXML private TableColumn<Car, String> colorEntry;

    private final ObservableList<Car> cars = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        licensePlateEntry.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        brandEntry.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelEntry.setCellValueFactory(new PropertyValueFactory<>("model"));
        colorEntry.setCellValueFactory(new PropertyValueFactory<>("color"));

        carsTable.setItems(cars);
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        App.setRoot("MyProfileScreen");
    }

    @FXML
    void loadCarInputs(ActionEvent event) {
        String licensePlate = licensePlateLoader.getText().trim();
        String brand = brandLoader.getText().trim();
        String model = modelLoader.getText().trim();
        String color = colorLoader.getText().trim();

        if (licensePlate.isEmpty() || brand.isEmpty()
                || model.isEmpty() || color.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        cars.add(new Car(licensePlate, brand, model, color));

        licensePlateLoader.clear();
        brandLoader.clear();
        modelLoader.clear();
        colorLoader.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Car {
        private final String licensePlate;
        private final String brand;
        private final String model;
        private final String color;

        public Car(String licensePlate, String brand, String model, String color) {
            this.licensePlate = licensePlate;
            this.brand = brand;
            this.model = model;
            this.color = color;
        }

        public String getLicensePlate() { return licensePlate; }
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public String getColor() { return color; }
    }
}