package schoolProject.Knicks.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, String> {
    List<Car> findByCustomerId(long customerId);
}
