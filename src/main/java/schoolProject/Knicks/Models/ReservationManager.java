package schoolProject.Knicks.Models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationManager {

    /** Flat demo rate per started hour; change here when pricing becomes product truth. */
    public static final BigDecimal HOURLY_RATE = new BigDecimal("4.00");

    @Autowired
    public ReservationRepo reservationRepo;

    public Reservation createReservation(Customer customer, Car car, String spot,
                                         LocalDateTime arrivalTime, LocalDateTime departureTime) {
        Objects.requireNonNull(customer, "customer is required");
        Objects.requireNonNull(car, "car is required");
        Objects.requireNonNull(arrivalTime, "arrivalTime is required");
        Objects.requireNonNull(departureTime, "departureTime is required");

        BigDecimal cost = calculateCost(arrivalTime, departureTime);

        Reservation reservation = Reservation.builder()
                .customer(customer)
                .car(car)
                .spot(spot == null ? null : spot.trim())
                .arrivalTime(arrivalTime)
                .departureTime(departureTime)
                .cost(cost)
                .build();
        return reservationRepo.save(reservation);
    }

    public BigDecimal calculateCost(LocalDateTime arrivalTime, LocalDateTime departureTime) {
        if (!departureTime.isAfter(arrivalTime)) {
            throw new IllegalArgumentException("Departure must be after arrival.");
        }

        long seconds = Duration.between(arrivalTime, departureTime).getSeconds();
        long billableHours = Math.max(1, (seconds + 3599) / 3600);
        return HOURLY_RATE.multiply(BigDecimal.valueOf(billableHours))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Reservation seedDemoReservation(Customer customer, Car car) {
        if (reservationRepo.count() > 0) {
            return null;
        }

        LocalDateTime arrival = LocalDateTime.now()
                .plusDays(1)
                .withHour(9).withMinute(0).withSecond(0).withNano(0);
        return createReservation(customer, car, "A1", arrival, arrival.plusHours(2));
    }
}
