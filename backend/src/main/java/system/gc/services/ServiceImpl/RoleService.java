package system.gc.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.RoleDTO;
import system.gc.entities.Role;
import system.gc.repositories.RoleRepository;

import java.util.List;

/**
 * @author Wisley Bruno Marques França
 * @since 27/01/2022
 * */

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream().map(RoleDTO::new).toList();
    }
}
