package schoolProject.Knicks.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Car {
    @Id
    String licensePlate;
    String manufacturer, model, color;
    @Column(name = "manufacture year")
    int year;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
}
