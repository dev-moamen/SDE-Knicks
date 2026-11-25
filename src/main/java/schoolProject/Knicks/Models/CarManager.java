package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManager {
    @Autowired
    public CarRepo carRepo;

    public Car managerCars(Customer customer) {
        Car car = carRepo.findAll().stream().findFirst().orElseGet(() ->
                Car.builder()
                        .licensePlate("1-ABC-123")
                        .manufacturer("Volkswagen")
                        .model("Golf")
                        .color("Red")
                        .year(2019)
                        .build());

        // The demo car may already exist from an earlier run; attach the owner.
        if (car.getCustomer() == null) {
            car.setCustomer(customer);
        }
        return carRepo.save(car);
    }

    public void saveCar(Car car) {
        carRepo.save(car);
    }
}
