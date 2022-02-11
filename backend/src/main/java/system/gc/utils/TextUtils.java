package system.gc.utils;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class TextUtils {
    //public static final long TOKEN_EXPIRATION_TIME = TimeUnit.MILLISECONDS.convert( 1, TimeUnit.DAYS ); //3600000;
    //public static final String PREFIX_BEARER = "Bearer";

    //public static final String ROLE_ADM = "Administrador";
    public static boolean isValid(String field) {
        return (field != null && !field.isEmpty());
    }
    public static Long TIME_TOKE_EXPIRATION = TimeUnit.MILLISECONDS.convert(8, TimeUnit.HOURS);
    public static Gson GSON = new Gson();


}