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

    @Autowired
    public ReservationRepo reservationRepo;

    public Reservation createReservation(Customer customer, Car car, ParkingSpot spot,
                                         LocalDateTime arrivalTime, LocalDateTime departureTime) {
        Objects.requireNonNull(customer, "customer is required");
        Objects.requireNonNull(car, "car is required");
        Objects.requireNonNull(spot, "spot is required");
        Objects.requireNonNull(arrivalTime, "arrivalTime is required");
        Objects.requireNonNull(departureTime, "departureTime is required");

        if (!spot.isAvailable()) {
            throw new IllegalArgumentException("That spot is closed for maintenance.");
        }
        if (!reservationRepo.findOverlapping(spot.getId(), arrivalTime, departureTime).isEmpty()) {
            throw new IllegalArgumentException("That spot is already reserved for part of that time.");
        }

        BigDecimal cost = calculateCost(spot.getHourlyRate(), arrivalTime, departureTime);

        Reservation reservation = Reservation.builder()
                .customer(customer)
                .car(car)
                .spot(spot)
                .arrivalTime(arrivalTime)
                .departureTime(departureTime)
                .cost(cost)
                .build();
        return reservationRepo.save(reservation);
    }

    public BigDecimal calculateCost(BigDecimal hourlyRate, LocalDateTime arrivalTime,
                                    LocalDateTime departureTime) {
        if (hourlyRate == null) {
            throw new IllegalArgumentException("Spot rate is missing.");
        }
        if (!departureTime.isAfter(arrivalTime)) {
            throw new IllegalArgumentException("Departure must be after arrival.");
        }

        long seconds = Duration.between(arrivalTime, departureTime).getSeconds();
        long billableHours = Math.max(1, (seconds + 3599) / 3600);
        return hourlyRate.multiply(BigDecimal.valueOf(billableHours))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Reservation seedDemoReservation(Customer customer, Car car, ParkingSpot spot) {
        if (reservationRepo.count() > 0) {
            return null;
        }

        LocalDateTime arrival = LocalDateTime.now()
                .withMinute(0).withSecond(0).withNano(0)
                .minusHours(1);
        return createReservation(customer, car, spot, arrival, arrival.plusHours(2));
    }
}
