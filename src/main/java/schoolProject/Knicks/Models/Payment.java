package schoolProject.Knicks.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Payment {
    @Id
    @GeneratedValue
    // id - layer of security
    long id;
    long number;
    String name;
    @Column(name= "expiration month")
    int month;
    @Column(name= "expiration year")
    int year;
    int cvv;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
}
