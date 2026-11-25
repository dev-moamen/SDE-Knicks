package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentManager {
    @Autowired
    public PaymentRepo paymentRepo;

    public Payment managerPayments(Customer customer) {
        Payment payment = paymentRepo.findAll().stream().findFirst().orElseGet(() ->
                Payment.builder()
                        .number(1234512345123451L)
                        .name(customer.getName())
                        .month(8)
                        .year(2029)
                    .build());

        // The demo card may already exist from an earlier run; attach the owner.
        if (payment.getCustomer() == null) {
            payment.setCustomer(customer);
        }
        return paymentRepo.save(payment);
    }

    public void savePayment(Payment payment) {
        paymentRepo.save(payment);
    }
}
