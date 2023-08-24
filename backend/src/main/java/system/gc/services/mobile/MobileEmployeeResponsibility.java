package system.gc.services.mobile;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileEmployeeResponsibility {
    /**
     * <p>Verifica se o funcionário pode ter acesso a um determinado recurso.</p>
     * <p>Exemplo: será verdadeiro se o funcionário estiver incluso em uma ordem de serviço.</p>
     * @param idEmployee - Identificação do funcionário em questão.
     * @param employees - Lista de funcionários que possuem acesso a determinado recurso.
     * @return Verdadeiro se e somente se o 'idEmployee' estiver incluso na lista de ids de 'employees'.
     */
    default boolean isResponsible(Integer idEmployee, Set<Employee> employees) {
        return employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toSet())
                .contains(idEmployee);
    }

    /**
     * <p>Verifica se o funcionário pode ter acesso a um determinado recurso.</p>
     * <p>Exemplo: será verdadeiro se o funcionário estiver incluso em uma ordem de serviço.</p>
     *
     * @param idEmployee    - Identificação do funcionário em questão.
     * @param employees     - Lista de funcionários que possuem acesso a determinado recurso.
     * @param messageSource - Recurso de strings
     * @throws AccessDeniedOrderServiceException - Lançará está exceção se o 'idEmployee' não estiver incluso na lista de ids de 'employees'.
     */
    default void isResponsible(Integer idEmployee, Set<Employee> employees, MessageSource messageSource) throws AccessDeniedOrderServiceException {
        boolean isResponsible = isResponsible(idEmployee, employees);
        if (!isResponsible)
            throw new AccessDeniedOrderServiceException(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
    }
}
