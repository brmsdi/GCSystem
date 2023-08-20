package system.gc.services.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import system.gc.dtos.ItemDTO;
import system.gc.dtos.OrderServiceDTO;
import system.gc.entities.Employee;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderService;

import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
public interface MobileOrderServiceService {

    /**
     * <p>Retorna as ordens de serviço correpondentes ao funcionário que está realizando a consulta.</p>
     * @param pageable - Páginação
     * @param id - Identificaçao do funcionário
     * @return Lista de ordens de serviço
     */
    Page<OrderServiceDTO> employeeOrders(Pageable pageable, Integer id);

    /**
     * <p>Retorna todas os dados detalhados da ordem de serviço.</p>
     * @param idOrderService - Identificação da ordem de serviço em questão
     * @param idEmployee - Identificação do funcionário que está realizando a consulta.
     * @return Ordem de serviço detalhada
     */
    OrderServiceDTO detailsOrderService(Integer idOrderService, Integer idEmployee) throws AccessDeniedOrderService;

    boolean isResponsible(Integer idEmployee, Set<Employee> employees);

    ItemDTO addItem(Integer idEmployee, Integer idOrderService, Integer idRepairRequest, ItemDTO itemDTO) throws AccessDeniedOrderService;
}
