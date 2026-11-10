package schoolProject.Knicks.Models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue
    long id;
    String name;
    String email;
    @Enumerated(EnumType.STRING)
    GenderType gender;
    LocalDate dateOfBirth;

    public enum GenderType {
        Male, Famale, Gay
    }
}
