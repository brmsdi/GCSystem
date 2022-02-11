package system.gc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.gc.entities.Employee;
import system.gc.entities.Lessee;
import system.gc.services.AuthenticateEntityByCPF;

import java.util.List;
import java.util.Optional;

public interface LesseeRepository extends JpaRepository<Lessee, Integer>, AuthenticateEntityByCPF<Lessee> {

    @Query("SELECT lessee FROM Lessee lessee JOIN FETCH lessee.status WHERE lessee IN :lessees")
    List<Lessee> loadLazyLessees(List<Lessee> lessees);

    @Query("SELECT lessee FROM Lessee lessee WHERE lessee.cpf LIKE :cpf")
    Optional<Lessee> findByCPF(String cpf);

    @Query("SELECT lessee FROM Lessee lessee WHERE lessee.cpf LIKE :cpf")
    Page<Lessee> findByCPF(Pageable pageable, String cpf);

    @Override
    @Query("SELECT lessee FROM Lessee lessee " +
            "JOIN FETCH lessee.status " +
            "WHERE lessee.cpf LIKE :cpf")
    Lessee getAuthentication(String cpf);
}
