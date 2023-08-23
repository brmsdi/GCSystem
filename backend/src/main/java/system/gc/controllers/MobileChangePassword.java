package system.gc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import system.gc.dtos.HttpMessageResponse;
import system.gc.dtos.TokenChangePasswordDTO;
import system.gc.dtos.TokenDTO;

/**
 * @author Wisley Bruno Marques França
 * @since 1.3
 */
public interface MobileChangePassword {

    /**
     * @param email Para qual será enviado o código gerado. Este código terá uma validade.
     * @return 'HttpMessageResponse' json com a mensagem de sucesso ou informação de erro.
     */
    @PostMapping("password/request-code")
    ResponseEntity<HttpMessageResponse> requestCode(@RequestParam String email);

    /**
     * @param email Endereço de email para qual o code será valido.
     * @param code Código Inserido pelo usuário. Este código será validado pelo sistema.
     * @return token Valido para a troca de senha. Este token será necessário para efetuar a atualização de senha.
     */
    @PostMapping(value = "password/receive-code")
    ResponseEntity<TokenDTO> receiveCode(@RequestParam String email, @RequestParam String code);

    /**
     * @param tokenChangePasswordDTO Este objeto contém os dados necessários para validar e efetuar a troca de senha do usuário.
     * @return 'HttpMessageResponse' json com a mensagem de sucesso
     */
    @PutMapping(value = "password/change")
    ResponseEntity<HttpMessageResponse> changePassword(@RequestBody TokenChangePasswordDTO tokenChangePasswordDTO);
}