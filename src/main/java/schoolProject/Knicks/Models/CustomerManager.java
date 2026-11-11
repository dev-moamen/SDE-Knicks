package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager {
    @Autowired
    public CustomerRepo customerRepo;

    public void manageCustomers() {
        Customer customer = Customer.builder()
       			.name("Lajos")
       			.gender(Customer.GenderType.Gay)
       		.build();
        customerRepo.save(customer);
    }
}
