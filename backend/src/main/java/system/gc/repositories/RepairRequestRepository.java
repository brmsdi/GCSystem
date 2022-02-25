package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.RepairRequest;

import java.util.List;

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

    @Query("SELECT repairRequest FROM RepairRequest repairRequest " +
            "JOIN FETCH repairRequest.status status " +
            "WHERE repairRequest IN :repairRequests AND status.id = :statusID")
    List<RepairRequest> checkIfTheRequestIsOpen(List<RepairRequest> repairRequests, Integer statusID);

}
