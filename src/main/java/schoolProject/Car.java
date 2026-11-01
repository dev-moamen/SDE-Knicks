package schoolProject;

public class Car {

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