package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManager {
    @Autowired
    public CarRepo carRepo;

    public Car managerCars(Customer customer) {
        Car car = Car.builder()
                .licensePlate("licensePlate")
                .color("Red")
                .customer(customer)
            .build();
        return carRepo.save(car);
    }

    public void saveCar(Car car) {
        carRepo.save(car);
    }
}
