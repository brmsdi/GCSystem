package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.RoleDTO;
import system.gc.entities.Role;
import system.gc.repositories.RoleRepository;
import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void save(List<Role> roleList)
    {
        log.info("Inserindo todas funções no banco de dados");
        roleRepository.saveAll(roleList);
    }

    public List<RoleDTO> findAll(Sort sort) {
        return roleRepository.findAll(sort).stream().map(RoleDTO::new).toList();
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        roleRepository.deleteAll();
    }
}
