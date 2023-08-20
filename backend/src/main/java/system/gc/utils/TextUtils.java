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
    public static final String ROLE_ADMINISTRATOR = "Administrador";
    public static final String ROLE_ADMINISTRATIVE_ASSISTANT = "Assistente administrativo";
    public static final String ROLE_COUNTER = "Contador";
    public static final String ROLE_ELECTRICIAN = "Eletricista";
    public static final String ROLE_PLUMBER = "Encanador";
    public static final String ROLE_GENERAL_SERVICES = "Serviços gerais";
    public static final String ROLE_LESSEE = "Locatário";
    public static final String STATUS_IN_PROGRESS = "Em andamento";
    public static final String STATUS_CONCLUDED = "Concluído";
    public static final String STATUS_CANCELED = "Cancelado";
    public static final String STATUS_OPEN = "Aberto";
    public static final String STATUS_LATE = "Atrasado";
    public static final String STATUS_ACTIVE = "Ativo";
    public static final String STATUS_INACTIVE = "Inativo";
    public static final String STATUS_DISABLED = "Desativado";
    public static final String STATUS_WAITING = "Aguardando";
    public static final String STATUS_VALID = "Válido";
    public static final String STATUS_INVALID = "Inválido";
    public static final String STATUS_RESCUED = "Resgatado";
    public static final String STATUS_DELETED = "Deletado";
    public static final String STATUS_AVAILABLE = "Disponível";
    public static final String STATUS_UNAVAILABLE = "Indisponível";
    public static final String STATUS_CROWDED = "Lotado";
    public static final String STATUS_CLOSED = "Encerrado";
    public static final String STATUS_EXPIRED = "Expirado";
    public static final String STATUS_OVERDUE = "Vencido";
    public static final String STATUS_PAID = "Pago";
    public static final String ACTIVITY_TYPE_REGISTERED = "Registrado";
    public static final String ACTIVITY_TYPE_UPDATED = "Atualizado";
    public static final String ACTIVITY_TYPE_DISABLED = "Desativado";
    public static final String ACTIVITY_TYPE_DELETED = "Deletado";
    public static final String TYPE_PROBLEM_ELECTRIC = "Elétrico";
    public static final String TYPE_PROBLEM_HYDRAULIC = "Hidráulico";
    public static final String TYPE_PROBLEM_OTHERS = "Outros";
    public static final String API_V1 = "/api/v1";
    public static final String API_V1_WEB = API_V1 + "/web";
    public static final String API_V1_MOBILE = API_V1 + "/mobile";

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