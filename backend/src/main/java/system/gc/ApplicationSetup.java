package system.gc;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import system.gc.dtos.EmployeeDTO;
import system.gc.dtos.RoleDTO;
import system.gc.dtos.SpecialtyDTO;
import system.gc.dtos.StatusDTO;
import system.gc.entities.Role;
import system.gc.entities.Specialty;
import system.gc.entities.Status;
import system.gc.repositories.RoleRepository;
import system.gc.repositories.SpecialtyRepository;
import system.gc.repositories.StatusRepository;
import system.gc.services.EmployeeService;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ApplicationSetup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SpecialtyRepository specialtyRepository;

    @Autowired
    StatusRepository statusRepository;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roleRepository.save(new Role("Administrador"));
        specialtyRepository.save(new Specialty("Desenvolvedor de Software"));
        statusRepository.save(new Status("Ativo"));
        List<Role> roles = roleRepository.findAll();
        List<Specialty> specialties = specialtyRepository.findAll();
        List<Status> status = statusRepository.findAll();
        SpecialtyDTO spEmployeeDTO = new SpecialtyDTO(specialties.get(0));
        Set<SpecialtyDTO> spEmployee = new HashSet<>();
        spEmployee.add(spEmployeeDTO);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        employeeService.save(new EmployeeDTO("Wisley Bruno Marques França",
                "12345",
                "1234567898",
                simpleDateFormat.parse("1995-12-06"),
                "srmarquesms@gmail.com",
                new Date(),
                new RoleDTO(roles.get(0)),
                spEmployee,
                null,
                new StatusDTO(status.get(0))));
    }
}
