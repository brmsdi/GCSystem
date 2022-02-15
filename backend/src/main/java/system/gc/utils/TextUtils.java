package system.gc.utils;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class TextUtils {
    public static Long TIME_TOKE_EXPIRATION = TimeUnit.MILLISECONDS.convert(8, TimeUnit.HOURS);
    public static Gson GSON = new Gson();

    public static boolean textIsValid(String text) {
        return text != null && !text.isEmpty();
    }

}