package system.gc.utils;

@Deprecated
public class TextUtils {
    //public static final long TOKEN_EXPIRATION_TIME = TimeUnit.MILLISECONDS.convert( 1, TimeUnit.DAYS ); //3600000;
    //public static final String PREFIX_BEARER = "Bearer";

    //public static final String ROLE_ADM = "Administrador";

    public static boolean isValid(String field) {
        return (field != null && !field.isEmpty());
    }

}