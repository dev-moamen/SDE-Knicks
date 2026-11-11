package schoolProject.Knicks;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import schoolProject.Knicks.Models.*;

@Service
@Getter
@RequiredArgsConstructor
public class H2DB {
    private final CustomerManager customerManager;
    private final CarManager carManager;
    private final PaymentManager paymentManager;
}
