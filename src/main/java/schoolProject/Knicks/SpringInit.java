package schoolProject.Knicks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import schoolProject.Knicks.Models.Customer;

@SpringBootApplication
public class SpringInit implements CommandLineRunner{
    @Autowired
    public H2DB h2db;

    public static void main(String[] args) {
		SpringApplication.run(SpringInit.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        Customer customer = h2db.getCustomerManager().manageCustomers();
        h2db.getCurrentUser().setCustomer(customer);
        h2db.getCarManager().managerCars(customer);
        h2db.getPaymentManager().managerPayments(customer);
	}
}
