package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentManager {
    @Autowired
    public PaymentRepo paymentRepo;

    public void managerPayments() {
        Payment payment = Payment.builder()
                .number(1234512345123451L)
            .build();
        paymentRepo.save(payment);
    }
}
