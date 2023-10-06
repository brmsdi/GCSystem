package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.RepairRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

public interface RepairRequestRepository extends JpaRepository<RepairRequest, Integer> {

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "JOIN FETCH repairRequest.typeProblem " +
            "JOIN FETCH repairRequest.lessee " +
            "JOIN FETCH repairRequest.condominium " +
            "JOIN FETCH repairRequest.status " +
            "JOIN FETCH repairRequest.items " +
            "WHERE repairRequest IN :repairRequests")
    List<RepairRequest> loadLazyRepairRequests(List<RepairRequest> repairRequests);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest WHERE repairRequest.lessee.cpf LIKE :cpf")
    Page<RepairRequest> findRepairRequestForLessee(Pageable pageable, String cpf);

    @Query("SELECT COUNT(*) FROM RepairRequest repairRequest " +
            "INNER JOIN repairRequest.status status " +
            "WHERE repairRequest IN :repairRequests AND status.id = :statusID")
    Long checkIfTheRequestIsOpen(List<RepairRequest> repairRequests, Integer statusID);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "JOIN FETCH repairRequest.status status " +
            "WHERE status.id IN :statusID")
    List<RepairRequest> perStatusRepairRequest(List<Integer> statusID);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "JOIN FETCH repairRequest.status status " +
            "WHERE (repairRequest.orderService.id = :ID) OR status.id IN :statusID")
    List<RepairRequest> findAllPerOrderServiceAndStatus(Integer ID, List<Integer> statusID);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "LEFT JOIN FETCH repairRequest.orderService orderService " +
            "LEFT JOIN FETCH orderService.employees " +
            "LEFT JOIN FETCH orderService.status " +
            "LEFT JOIN FETCH repairRequest.items " +
            "LEFT JOIN FETCH repairRequest.typeProblem " +
            "WHERE repairRequest.id = :idRepairRequest")
    Optional<RepairRequest> findRepairRequestToAddOrRemoveItem(Integer idRepairRequest);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "WHERE repairRequest.lessee.id = :id")
    Page<RepairRequest> findRepairRequestForLessee(Pageable pageable, Integer id);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "LEFT JOIN FETCH repairRequest.typeProblem " +
            "LEFT JOIN FETCH repairRequest.status " +
            "LEFT JOIN FETCH repairRequest.lessee " +
            "LEFT JOIN FETCH repairRequest.condominium " +
            "WHERE repairRequest IN :repairRequestList")
    List<RepairRequest> loadLazyRepairRequestsForViewListMobile(List<RepairRequest> repairRequestList);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "WHERE repairRequest.id = :keySearch AND repairRequest.lessee.id = :idLessee")
    Page<RepairRequest> searchRepairRequestFromLessee(Pageable pageable, Integer idLessee, Integer keySearch);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "LEFT JOIN FETCH repairRequest.orderService orderService " +
            "LEFT JOIN FETCH orderService.employees " +
            "LEFT JOIN FETCH orderService.status " +
            "LEFT JOIN FETCH repairRequest.items " +
            "LEFT JOIN FETCH repairRequest.typeProblem " +
            "LEFT JOIN FETCH repairRequest.lessee lessee " +
            "LEFT JOIN FETCH repairRequest.condominium " +
            "WHERE repairRequest.id = :id AND lessee.id = :idLessee")
    Optional<RepairRequest> detailsRepairRequestFromLessee(Integer idLessee, Integer id);

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "LEFT JOIN FETCH repairRequest.status " +
            "LEFT JOIN FETCH repairRequest.lessee " +
            "WHERE repairRequest.id = :id")
    Optional<RepairRequest> searchToDelete(Integer id);
}
