package schoolProject.Knicks.Models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepo extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByFloorIdOrderByCodeAsc(long floorId);

    long countByFloorId(long floorId);

    Optional<ParkingSpot> findByCode(String code);
}
