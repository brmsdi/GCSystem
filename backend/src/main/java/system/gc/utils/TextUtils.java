package system.gc.utils;

import com.google.gson.Gson;

import java.util.UUID;
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