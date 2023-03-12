package system.gc.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    public static NumberFormat getNumberInstanceUS()
    {
        NumberFormat numberInstance = DecimalFormat.getNumberInstance(Locale.US);
        numberInstance.setMinimumFractionDigits(2);
        numberInstance.setGroupingUsed(false);
        return numberInstance;
    }
}
