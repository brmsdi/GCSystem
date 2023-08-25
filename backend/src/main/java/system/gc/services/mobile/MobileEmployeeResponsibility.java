package system.gc.services.mobile;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;

import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileEmployeeResponsibility {
    /**
     * <p>Verifica se o funcionário/locatário pode ter acesso a um determinado recurso.</p>
     * <p>Exemplo: será verdadeiro se o id do funcionário ou locatário estiver incluso em um determinado recurso.</p>
     *
     * @param id  - Identificação chave em questão.
     * @param ids - Lista de números de identificação correspondentes a registros que possuem acesso a determinado recurso.
     * @return Verdadeiro se e somente se o 'id' estiver incluso na lista de 'ids''.
     */
    default boolean isResponsible(Integer id, Set<Integer> ids) {
        return ids.contains(id);
    }

    /**
     * <p>Verifica se o funcionário/locatário pode ter acesso a um determinado recurso.</p>
     *
     * @param id            - Identificação chave em questão.
     * @param ids           - Lista de números de identificação correspondentes a registros que possuem acesso a determinado recurso.
     * @param messageSource - Recurso de strings
     * @throws AccessDeniedOrderServiceException - Lançará está exceção se o 'idEmployee' não estiver incluso na lista de ids de 'employees'.
     */
    default void isResponsible(Integer id, Set<Integer> ids, MessageSource messageSource) throws AccessDeniedOrderServiceException {
        boolean isResponsible = isResponsible(id, ids);
        if (!isResponsible)
            throw new AccessDeniedOrderServiceException(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
    }
}
