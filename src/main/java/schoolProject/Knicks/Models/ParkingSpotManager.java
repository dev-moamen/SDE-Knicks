package schoolProject.Knicks.Models;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.IntFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotManager {

    @Autowired
    public ParkingFloorRepo floorRepo;

    @Autowired
    public ParkingSpotRepo spotRepo;

    public void seedParkingLot() {
        if (floorRepo.count() > 0) {
            return;
        }

        ParkingFloor basement = saveFloor(-1, "Basement", "Compact and standard parking");
        ParkingFloor ground = saveFloor(0, "Ground", "Accessible, EV charging, and standard");
        ParkingFloor levelOne = saveFloor(1, "Level 1", "Accessible, EV charging, and standard");
        ParkingFloor rooftop = saveFloor(2, "Rooftop", "Open-air standard parking");

        seedSpots(basement, "B", 10,
                index -> index <= 4 ? SpotType.COMPACT : SpotType.STANDARD);
        seedSpots(ground, "G", 16,
                index -> index <= 2 ? SpotType.ACCESSIBLE
                        : index <= 6 ? SpotType.EV : SpotType.STANDARD);
        seedSpots(levelOne, "L1", 16,
                index -> index <= 2 ? SpotType.ACCESSIBLE
                        : index <= 6 ? SpotType.EV : SpotType.STANDARD);
        seedSpots(rooftop, "R", 8, index -> SpotType.STANDARD);

        // Show that maintenance takes a spot out of service.
        closeSpot("B-05");
        closeSpot("G-16");
    }

    public Optional<ParkingSpot> findByCode(String code) {
        return spotRepo.findByCode(code);
    }

    private ParkingFloor saveFloor(int level, String name, String description) {
        return floorRepo.save(ParkingFloor.builder()
                .level(level)
                .name(name)
                .description(description)
                .build());
    }

    private void seedSpots(ParkingFloor floor, String prefix, int count,
                           IntFunction<SpotType> typeFor) {
        for (int index = 1; index <= count; index++) {
            SpotType type = typeFor.apply(index);
            spotRepo.save(ParkingSpot.builder()
                    .code(String.format("%s-%02d", prefix, index))
                    .floor(floor)
                    .type(type)
                    .available(true)
                    .hourlyRate(rateFor(type, floor.getLevel()))
                    .build());
        }
    }

    private void closeSpot(String code) {
        spotRepo.findByCode(code).ifPresent(spot -> {
            spot.setAvailable(false);
            spotRepo.save(spot);
        });
    }

    private BigDecimal rateFor(SpotType type, int level) {
        return switch (type) {
            case EV -> new BigDecimal("5.50");
            case COMPACT -> new BigDecimal("2.50");
            case ACCESSIBLE -> new BigDecimal("3.00");
            case STANDARD -> new BigDecimal(level == -1 ? "3.00" : level == 2 ? "2.50" : "4.00");
        };
    }
}
