package schoolProject.Knicks.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByCustomerId(long customerId);
}
