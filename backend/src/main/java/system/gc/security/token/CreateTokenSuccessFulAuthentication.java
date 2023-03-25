package system.gc.security.token;

import system.gc.dtos.TokenDTO;
import system.gc.utils.TextUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

public interface CreateTokenSuccessFulAuthentication {
    default void createTokenSuccessFulAuthentication(HttpServletResponse response,
                                                     final Map<String, String> params) throws Exception {
        writeToken(JWTService.createTokenJWT(params, LocalDateTime.now(), 8), response);
    }

    default void writeToken(String token, HttpServletResponse response) throws IOException {
        response.getWriter().print(TextUtils.GSON.toJson(TokenDTO.builder().type("Bearer").token(token).build()));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
