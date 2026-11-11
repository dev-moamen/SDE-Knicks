package schoolProject.Knicks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnicksApplication implements CommandLineRunner{
    @Autowired
    public H2DB h2db;

    public static void main(String[] args) {
		SpringApplication.run(KnicksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        h2db.getCustomerManager().manageCustomers();
        h2db.getCarManager().managerCars();
	}
}
