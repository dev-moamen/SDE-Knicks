package schoolProject.Knicks.Models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerIdOrderByArrivalTimeDesc(long customerId);
}
