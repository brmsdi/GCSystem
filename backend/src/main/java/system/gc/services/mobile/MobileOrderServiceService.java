package system.gc.services.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileOrderServiceService {

    /**
     * <p>Retorna as ordens de serviço correpondentes ao funcionário que está realizando a consulta.</p>
     *
     * @param pageable - Páginação
     * @param id       - Identificaçao do funcionário
     * @return Lista de ordens de serviço
     */
    Page<OrderServiceDTO> employeeOrders(Pageable pageable, Integer id);

    /**
     * <p>Retorna todas os dados detalhados da ordem de serviço.</p>
     *
     * @param idOrderService - Identificação da ordem de serviço em questão
     * @param idEmployee     - Identificação do funcionário que está realizando a consulta.
     * @return Ordem de serviço detalhada
     */
    OrderServiceDTO detailsOrderService(Integer idOrderService, Integer idEmployee) throws AccessDeniedOrderServiceException;

    default boolean isResponsible(Integer idEmployee, Set<Employee> employees) {
        return employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toSet())
                .contains(idEmployee);
    }
}
