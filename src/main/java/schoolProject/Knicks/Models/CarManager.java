package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManager {
    @Autowired
    public CarRepo carRepo;

    public void managerCars() {
        Car car = Car.builder()
                .licensePlate("licensePlate")
                .color("Red")
            .build();
        carRepo.save(car);
    }

    public void saveCar(Car car) {
        carRepo.save(car);
    }
}
