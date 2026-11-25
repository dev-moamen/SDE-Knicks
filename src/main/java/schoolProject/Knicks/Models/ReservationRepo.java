package schoolProject.Knicks.Models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerIdOrderByArrivalTimeDesc(long customerId);

    @Query("select r from Reservation r where r.spot.id = :spotId "
            + "and r.arrivalTime < :departure and r.departureTime > :arrival")
    List<Reservation> findOverlapping(@Param("spotId") long spotId,
                                      @Param("arrival") LocalDateTime arrival,
                                      @Param("departure") LocalDateTime departure);

    @Query("select r from Reservation r where r.arrivalTime <= :now and r.departureTime > :now")
    List<Reservation> findActiveAt(@Param("now") LocalDateTime now);
}
