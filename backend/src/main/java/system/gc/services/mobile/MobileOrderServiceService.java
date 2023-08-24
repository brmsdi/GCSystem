package system.gc.services.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import system.gc.dtos.OrderServiceDTO;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileOrderServiceService extends MobileOrderServiceStatusUtils, MobileEmployeeResponsibility {

    /**
     * <p>Retorna as ordens de serviço correspondentes ao funcionário que está realizando a consulta.</p>
     * @param pageable - Parâmetros de paginação.
     * @param id       - Identificação do funcionário
     * @return Lista de ordens de serviço
     */
    Page<OrderServiceDTO> employeeOrders(Pageable pageable, Integer id);

    /**
     * <p>Retorna todas os dados detalhados da ordem de serviço.</p>
     * @param idOrderService - Identificação da ordem de serviço em questão
     * @param idEmployee     - Identificação do funcionário que está realizando a consulta.
     * @return Ordem de serviço detalhada
     */
    OrderServiceDTO detailsOrderService(Integer idOrderService, Integer idEmployee) throws AccessDeniedOrderServiceException;


    /**
     * <p>Realiza uma busca no banco de dados, utilizando o 'id' como chava de pesquisa.</p>
     * <p>A ordem de serviço só será retornada se o 'id' corresponder a um registro no banco de dados e o funcionário tiver permissão para acessar a mesma.</p>
     * @param pageable Parâmetros de paginação.
     * @param idEmployee - Identificação do funcionário que está realizando a consulta
     * @param idOrderService - Id da ordem de serviço utilizado na busca no banco de dados
     * @return Busca paginada com a ordem de serviço localizada.
     */
    Page<OrderServiceDTO> findByIdFromEmployee(Pageable pageable, Integer idEmployee, Integer idOrderService);

    void closeOrderService(Integer idEmployee, Integer idOrderService) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException;
}
