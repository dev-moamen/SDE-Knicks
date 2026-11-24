package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager {
    @Autowired
    public CustomerRepo customerRepo;

    public Customer manageCustomers() {
        return customerRepo.findAll().stream().findFirst().orElseGet(() -> {
            Customer customer = Customer.builder()
                    .name("Lajos")
                    .gender(Customer.GenderType.Gay)
                    .build();
            return customerRepo.save(customer);
        });
    }
}
