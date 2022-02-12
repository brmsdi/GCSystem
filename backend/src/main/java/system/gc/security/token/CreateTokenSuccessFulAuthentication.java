package system.gc.security.token;

import org.springframework.security.core.Authentication;
import system.gc.dtos.TokenDTO;
import system.gc.utils.TextUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CreateTokenSuccessFulAuthentication {
    default void createTokenSuccessFulAuthentication(HttpServletResponse response,
                                                     Authentication authResult,
                                                     final String TYPE) throws Exception, IOException {
        writeToken(JWTService.createTokenJWT(authResult, TYPE), response);

    }

    private void writeToken(String token, HttpServletResponse response) throws IOException {
        response.getWriter().print(TextUtils.GSON.toJson(TokenDTO.builder().type("Bearer").token(token).build()));
        response.setStatus(HttpServletResponse.SC_GONE);
    }

}
