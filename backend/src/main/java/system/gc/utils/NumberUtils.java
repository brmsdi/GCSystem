package system.gc.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public class NumberUtils {

    private NumberUtils() {
    }

    public static String formatCoin(Integer maximumFractionDigits, Integer minimumFractionDigits, double value) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numberInstance.setMaximumFractionDigits(maximumFractionDigits);
        numberInstance.setMinimumFractionDigits(minimumFractionDigits);
        var symbol = numberInstance.getCurrency().getSymbol();
        return String.format("%s %s", symbol, numberInstance.format(value));
    }
}