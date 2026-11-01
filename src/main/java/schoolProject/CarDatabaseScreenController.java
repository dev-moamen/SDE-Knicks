package schoolProject;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CarDatabaseScreenController {

    @FXML
    private Button carDatabaseBack;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> licensePlateEntry;

    @FXML
    private TableColumn<Car, String> brandEntry;

    @FXML
    private TableColumn<Car, String> modelEntry;

    @FXML
    private TableColumn<Car, String> colorEntry;

    @FXML
    private void initialize() {
        licensePlateEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLicensePlate()));
        brandEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBrand()));
        modelEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getModel()));
        colorEntry.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getColor()));

        carsTable.setPlaceholder(new Label("No cars added yet"));

        // the same list the My Cars screen adds to
        carsTable.setItems(MyCarsScreenController.cars);
    }

    @FXML
    void switchToAdminScreen(ActionEvent event) throws IOException {
        App.setRoot("AdminScreen");
    }
}