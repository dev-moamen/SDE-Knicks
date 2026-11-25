package schoolProject.Knicks.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue
    long id;

    @Column(nullable = false, unique = true)
    String code;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    ParkingFloor floor;

    @Enumerated(EnumType.STRING)
    SpotType type;

    /** False means the spot is closed for maintenance, not just occupied. */
    boolean available;

    @Column(precision = 6, scale = 2)
    BigDecimal hourlyRate;
}
