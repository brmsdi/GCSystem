package system.gc.services.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import system.gc.dtos.ItemDTO;
import system.gc.dtos.MobileRepairRequestToSaveDTO;
import system.gc.dtos.RepairRequestDTO;
import system.gc.dtos.ScreenNewRepairRequestMobileDataDTO;
import system.gc.entities.Item;
import system.gc.entities.RepairRequest;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalSelectedRepairRequestsException;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */
public interface MobileRepairRequestService extends MobileOrderServiceStatusUtils, MobileEmployeeResponsibility {
    RepairRequest findRepairRequestToAddOrRemoveItem(Integer id);

    default Optional<Item> getItemInItems(Integer idItem, Set<Item> items) {
        return items.stream()
                .filter(item -> Objects.equals(item.getId(), idItem))
                .findFirst();
    }

    ItemDTO addItem(Integer idEmployee, Integer idRepairRequest, ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException;

    void removeItem(Integer idEmployee, Integer idRepairRequest, Integer idItem) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException;

    /**
     * <p>Retorna as solicitações de reparo correspondentes ao locatário que está realizando a consulta.</p>
     *
     * @param pageable - Parâmetros de paginação.
     * @param id       - Identificação do locatário
     * @return Lista de solicitações de reparo
     */
    Page<RepairRequestDTO> lesseeRepairRequests(Pageable pageable, Integer id);

    /**
     * <p>Buscar solicitação de reparo por id.</p>
     * <p>Essa busca só retornará a solicitação de reparo criada pelo locatário em questão.</p>
     *
     * @param pageable  - Paginação.
     * @param idLessee  - Identificador do locatário que está acessando o recurso.
     * @param keySearch - Identificador da solicitação de reparo
     * @return Registro da solicitação de reparo referente a chave de pesquisa 'keySearch'.
     */
    Page<RepairRequestDTO> searchById(Pageable pageable, Integer idLessee, Integer keySearch);

    RepairRequestDTO save(Integer lesseeID, MobileRepairRequestToSaveDTO mobileRepairRequestToSaveDTO) throws EntityNotFoundException;

    RepairRequestDTO update(Integer lesseeID, RepairRequestDTO repairRequestDTO) throws ClassNotFoundException;

    void delete(Integer lesseeID, Integer id) throws EntityNotFoundException, IllegalSelectedRepairRequestsException;

    ScreenNewRepairRequestMobileDataDTO screenData(Integer idLessee);

    RepairRequestDTO details(Integer idLessee, Integer idRepairRequest) throws ClassNotFoundException;
}