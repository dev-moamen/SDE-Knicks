package schoolProject.Knicks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import schoolProject.Knicks.Models.*;

@SpringBootApplication
public class KnicksApplication implements CommandLineRunner{
    @Autowired
    public CustomerManager cutomerManager;
    @Autowired
    public CarManager carManager;

    public static void main(String[] args) {
		SpringApplication.run(KnicksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        cutomerManager.manageCustomers();
        carManager.managerCars();
	}
}
