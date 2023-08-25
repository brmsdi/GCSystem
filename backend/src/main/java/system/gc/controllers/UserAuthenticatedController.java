package system.gc.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import system.gc.exceptionsAdvice.exceptions.UserAuthenticatedException;
import system.gc.security.UserAuthenticated;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface UserAuthenticatedController {

    /**
     * <p>Retorna o usuário que está autenticado no sistema conforme o token recebido na requisição.<p/>
     * <P>Funcionários e locatários podem realizar autenticação no sistema.</P>
     *
     * @param clazz - Implementação de UserAuthenticated. LesseeUserDetails ou EmployeeUserDetails.
     * @param <E>   - Entidade localizada conforme o token de acesso. Podendo uma entidade 'Employee' ou 'Lessee'.
     * @param <T>   - Implementação de 'UserAuthenticated' onde E corresponde a uma entidade autenticável.
     * @return - Retorna a entidade correspondente ao usuário localizado.
     * @throws UserAuthenticatedException - Lançada se o details autenticado não for compatível com a instância necessária para acessar o recurso.
     * @see system.gc.security.LesseeUserDetails - Implementação de UserAuthenticated correspondente a locatários.
     * @see system.gc.security.EmployeeUserDetails - Implementação de UserAuthenticated correspondente a funcionários.
     */
    default <E, T extends UserAuthenticated<E>> E getUserAuthenticated(Class<T> clazz) throws UserAuthenticatedException {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (clazz.isInstance(details)) {
            return clazz.cast(details).getUserAuthenticated();
        }
        throw new UserAuthenticatedException(messageSource().getMessage("TEXT_ERROR_USER_AUTHENTICATED", null, LocaleContextHolder.getLocale()));
    }

    MessageSource messageSource();
}