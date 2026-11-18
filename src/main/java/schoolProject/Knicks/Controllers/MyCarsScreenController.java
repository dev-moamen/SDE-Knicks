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
import schoolProject.Knicks.Models.Car;

public class MyCarsScreenController {

    @FXML private TextField licensePlateLoader;
    @FXML private TextField brandLoader;
    @FXML private TextField modelLoader;
    @FXML private TextField colorLoader;
    @FXML private TextField yearLoader;

    @FXML private TableView<Car> carsTable;
    @FXML private TableColumn<Car, String> licensePlateEntry;
    @FXML private TableColumn<Car, String> brandEntry;
    @FXML private TableColumn<Car, String> modelEntry;
    @FXML private TableColumn<Car, String> colorEntry;
    @FXML private TableColumn<Car, Integer> yearEntry;

    private final ObservableList<Car> cars = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        licensePlateEntry.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        brandEntry.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelEntry.setCellValueFactory(new PropertyValueFactory<>("model"));
        colorEntry.setCellValueFactory(new PropertyValueFactory<>("color"));
        yearEntry.setCellValueFactory(new PropertyValueFactory<>("year"));

        carsTable.setItems(cars);
    }

    @FXML
    void switchToMyProfileScreen(ActionEvent event) throws IOException {
        JavaFX.setRoot("/javafxml/MyProfileScreen");
    }

    @FXML
    void loadCarInputs(ActionEvent event) {
        String licensePlate = licensePlateLoader.getText().trim();
        String manufacturer = brandLoader.getText().trim();
        String model = modelLoader.getText().trim();
        String color = colorLoader.getText().trim();
        String yearText = yearLoader.getText().trim();

        if (licensePlate.isEmpty() || manufacturer.isEmpty()
                || model.isEmpty() || color.isEmpty() || yearText.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            showAlert("Enter a valid manufacture year.");
            return;
        }

        Car car = Car.builder()
                .licensePlate(licensePlate)
                .manufacturer(manufacturer)
                .model(model)
                .color(color)
                .year(year)
                .build();

        cars.add(car);

        licensePlateLoader.clear();
        brandLoader.clear();
        modelLoader.clear();
        colorLoader.clear();
        yearLoader.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
