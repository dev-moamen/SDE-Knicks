package schoolProject.Knicks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import schoolProject.Knicks.Models.Car;
import schoolProject.Knicks.Models.Customer;
import schoolProject.Knicks.Models.ParkingSpot;

@SpringBootApplication
public class SpringInit implements CommandLineRunner{
    @Autowired
    public H2DB h2db;

    public static void main(String[] args) {
		SpringApplication.run(SpringInit.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        h2db.getParkingSpotManager().seedParkingLot();

        Customer customer = h2db.getCustomerManager().manageCustomers();
        h2db.getCurrentUser().setCustomer(customer);
        Car car = h2db.getCarManager().managerCars(customer);
        h2db.getPaymentManager().managerPayments(customer);

        ParkingSpot demoSpot = h2db.getParkingSpotManager().findByCode("G-01").orElse(null);
        if (demoSpot != null) {
            h2db.getReservationManager().seedDemoReservation(customer, car, demoSpot);
        }
	}
}
