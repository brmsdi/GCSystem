package system.gc.utils;

import com.google.gson.Gson;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public class TextUtils {
    public static Long TIME_TOKEN_CHANGE_PASSWORD_EXPIRATION = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES);
    public static Gson GSON = new Gson();
    public static boolean textIsValid(String text) {
        return text != null && !text.isEmpty();
    }
    public static final String ROLE_ADMINISTRATOR = "ADMINISTRADOR";
    public static final String ROLE_ADMINISTRATIVE_ASSISTANT = "ASSISTENTE ADMINISTRATIVO";
    public static final String ROLE_COUNTER = "CONTADOR";
    public static final String API_V1 = "/api/v1";
    public static final String API_V1_WEB = API_V1 + "/web";

    public static String generateTXID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String initLowerLetter(String text)
    {
        String regex = ".*[A-Z].*";
        if (text.length() == 0) throw new IllegalArgumentException("O texto não pode ser vázio");
        if (!String.valueOf(text.charAt(0)).matches(regex)) return text.concat("Object");
        String startText = String.valueOf(text.charAt(0)).toLowerCase();
        return startText.concat(text.substring(1));
    }
}