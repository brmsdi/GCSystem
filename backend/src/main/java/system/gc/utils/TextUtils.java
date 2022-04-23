package system.gc.utils;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class TextUtils {
    public static Long TIME_TOKEN_AUTH_EXPIRATION = TimeUnit.MILLISECONDS.convert(8, TimeUnit.HOURS);
    public static Long TIME_TOKEN_CHANGE_PASSWORD_EXPIRATION = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES);
    public static Gson GSON = new Gson();
    public static boolean textIsValid(String text) {
        return text != null && !text.isEmpty();
    }
    public static final String ROLE_ADMINISTRATOR = "ADMINISTRADOR";
    public static final String ROLE_ADMINISTRATIVE_ASSISTANT = "ASSISTENTE ADMINISTRATIVO";
    public static final String ROLE_COUNTER = "CONTADOR";
}