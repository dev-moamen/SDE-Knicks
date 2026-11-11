package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManager {
    @Autowired
    public CarRepo carRepo;

    public void managerCars() {
        Car car = Car.builder()
                .color("Red")
            .build();
        carRepo.save(car);
    }
}
