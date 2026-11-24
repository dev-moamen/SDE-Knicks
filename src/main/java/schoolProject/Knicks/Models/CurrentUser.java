package schoolProject.Knicks.Models;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

// Holds the customer the desktop app is currently acting as; a real login would replace this, this is temp
 
@Service
@Getter
@Setter
public class CurrentUser {
    private Customer customer;
}
