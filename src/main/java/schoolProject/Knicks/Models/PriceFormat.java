package schoolProject.Knicks.Models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Small formatting helper so every price uses the same euro formatting. */
public final class PriceFormat {

    private PriceFormat() {
    }

    public static String euro(BigDecimal amount) {
        BigDecimal value = amount == null ? BigDecimal.ZERO : amount;
        return "\u20ac" + value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
