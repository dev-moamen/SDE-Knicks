package schoolProject.Knicks.Models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFloorRepo extends JpaRepository<ParkingFloor, Long> {
    List<ParkingFloor> findAllByOrderByLevelAsc();
}
