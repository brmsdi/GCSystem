package system.gc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import system.gc.dtos.*;
import system.gc.entities.ActivityType;
import system.gc.entities.Role;
import system.gc.entities.Specialty;
import system.gc.entities.Status;
import system.gc.repositories.ActivityTypeRepository;
import system.gc.repositories.RoleRepository;
import system.gc.repositories.SpecialtyRepository;
import system.gc.repositories.StatusRepository;
import system.gc.services.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class ApplicationSetup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SpecialtyRepository specialtyRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    LocalizationService localizationService;

    @Autowired
    CondominiumService condominiumService;

    @Autowired
    ContractService contractService;

    @Autowired
    LesseeService lesseeService;

    @Autowired
    DebtService debtService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    Environment environment;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!Arrays.stream(environment.getActiveProfiles()).toList().contains("test")) return;
        roleRepository.save(new Role("Administrador"));
        specialtyRepository.save(new Specialty("Desenvolvedor de Software"));
        statusRepository.save(new Status("Ativo"));
        statusRepository.save(new Status("Inativo"));
        statusRepository.save(new Status("Aberto"));
        statusRepository.save(new Status("Desativado"));
        activityTypeRepository.save(new ActivityType("Registrado"));
        activityTypeRepository.save(new ActivityType("Atualizado"));
        activityTypeRepository.save(new ActivityType("Desativado"));
        List<Role> roles = roleRepository.findAll();
        List<Specialty> specialties = specialtyRepository.findAll();
        List<Status> status = statusRepository.findAll();
        SpecialtyDTO spEmployeeDTO = new SpecialtyDTO(specialties.get(0));
        Set<SpecialtyDTO> spEmployee = new HashSet<>();
        spEmployee.add(spEmployeeDTO);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            employeeService.save(new EmployeeDTO("Wisley Bruno Marques França",
                    "2343435",
                    "1234567898",
                    simpleDateFormat.parse("1995-12-06"),
                    "srmarquesms@gmail.com",
                    new Date(),
                    "12345678",
                    new RoleDTO(roles.get(0)),
                    spEmployee,
                    null,
                    new StatusDTO(status.get(0))));

            employeeService.save(new EmployeeDTO("Amanda Silva",
                    "695854",
                    "12578678980",
                    simpleDateFormat.parse("2000-12-06"),
                    "exemple@gmail.com",
                    new Date(),
                    "67896755656",
                    new RoleDTO(roles.get(0)),
                    spEmployee,
                    null,
                    new StatusDTO(status.get(0))));

            LocalizationDTO localizationDTO = localizationService.save(new LocalizationDTO("Flores", "920", "69058200"));
            LocalizationDTO localizationDTO1 = localizationService.save(new LocalizationDTO("Parque 10", "Nov H", "69058223"));

            CondominiumDTO condominiumDTO = new CondominiumDTO("Villa Lobos",
                    "A30",
                    20,
                    new StatusDTO(status.get(0)),
                    new LocalizationCondominiumDTO("500", localizationDTO));

            CondominiumDTO condominiumDTO1 = new CondominiumDTO("PTU",
                    "PTU30",
                    8,
                    new StatusDTO(status.get(0)),
                    new LocalizationCondominiumDTO("900", localizationDTO1));

            condominiumService.save(condominiumDTO);
            condominiumService.save(condominiumDTO1);

            for(int i = 5; i < 15; i++) {
                CondominiumDTO condominiumDTO3 = new CondominiumDTO("PTU",
                        "PTU30" + i,
                        8 + i,
                        new StatusDTO(status.get(0)),
                        new LocalizationCondominiumDTO("900" + i, localizationDTO1));
                condominiumService.save(condominiumDTO3);
            }

            for(int i = 0; i < 8; i++) {
                LesseeDTO lesseeDTO = new LesseeDTO(
                        "Daniel" + i,
                        "635986" + i,
                        "1256325667" + i,
                        simpleDateFormat.parse("2003-06-02"),
                        "daniel@gmail.com",
                        "9298863526" + i,
                        "785452545" + i,
                        new StatusDTO(status.get(0))
                );
                lesseeService.save(lesseeDTO);
            }

            Page<CondominiumDTO> condominiumDTOPage = condominiumService.listPaginationCondominium(PageRequest.of(0, 5));
            Page<LesseeDTO> lesseeDTOPage = lesseeService.listPaginationLessees(PageRequest.of(0, 5));
            ContractDTO contractDTO = new ContractDTO(simpleDateFormat.parse("2022-02-05"),
                    1200.00,
                    5,
                    15,
                    simpleDateFormat.parse("2022-07-05"),
                    60,
                    new StatusDTO(status.get(0)),
                    condominiumDTOPage.toList().get(0),
                    lesseeDTOPage.toList().get(0));

            ContractDTO contractDTO2 = new ContractDTO(simpleDateFormat.parse("2022-02-10"),
                    1100.00,
                    10,
                    20,
                    simpleDateFormat.parse("2022-07-05"),
                    10,
                    new StatusDTO(status.get(0)),
                    condominiumDTOPage.toList().get(1),
                    lesseeDTOPage.toList().get(1));
            contractService.save(contractDTO);
            contractService.save(contractDTO);
            contractService.save(contractDTO2);

            DebtDTO debtDTO = new DebtDTO(simpleDateFormat.parse("2022-03-07"),
                    2000,
                    new StatusDTO().toDTO(status.get(2)),
                    null,
                    lesseeDTOPage.toList().get(0));
            debtService.save(debtDTO);

/*
            employeeService.update(new EmployeeDTO("Amanda2 Silva",
                    "695854",
                    "12578678980",
                    simpleDateFormat.parse("2000-12-06"),
                    "exemple@gmail.com",
                    new Date(),
                    new RoleDTO(roles.get(0)),
                    spEmployee,
                    null,
                    new StatusDTO(status.get(0))));


                    */

        } catch (IllegalArgumentException e){
            log.warn(e.getMessage());

        }

    }
}

/*
Hibernate:
    select
        employee0_.id as id1_4_,
        employee0_.birth_date as birth_da2_4_,
        employee0_.cpf as cpf3_4_,
        employee0_.email as email4_4_,
        employee0_.hiring_date as hiring_d5_4_,
        employee0_.name as name6_4_,
        employee0_.rg as rg7_4_,
        employee0_.fk_role_id as fk_role_8_4_,
        employee0_.fk_status_id as fk_statu9_4_
    from
        employee employee0_ limit ?
Hibernate:
    select
        role0_.id as id1_10_0_,
        role0_.name as name2_10_0_
    from
        role role0_
    where
        role0_.id=?
Hibernate:
    select
        status0_.id as id1_12_0_,
        status0_.name as name2_12_0_
    from
        status status0_
    where
        status0_.id=?
Hibernate:
    select
        specialtie0_.employee_id as employee1_5_0_,
        specialtie0_.specialty_id as specialt2_5_0_,
        specialty1_.id as id1_11_1_,
        specialty1_.name as name2_11_1_
    from
        employee_specialty specialtie0_
    inner join
        specialty specialty1_
            on specialtie0_.specialty_id=specialty1_.id
    where
        specialtie0_.employee_id=?
Hibernate:
    select
        movements0_.fk_employee_id as fk_emplo7_9_0_,
        movements0_.id as id1_9_0_,
        movements0_.id as id1_9_1_,
        movements0_.fk_activity_type_id as fk_activ5_9_1_,
        movements0_.fk_debt_id as fk_debt_6_9_1_,
        movements0_.due_date as due_date2_9_1_,
        movements0_.fk_employee_id as fk_emplo7_9_1_,
        movements0_.move_date_and_time as move_dat3_9_1_,
        movements0_.previous_value as previous4_9_1_,
        activityty1_.id as id1_0_2_,
        activityty1_.name as name2_0_2_,
        debt2_.id as id1_3_3_,
        debt2_.due_date as due_date2_3_3_,
        debt2_.fk_lessee_id as fk_lesse4_3_3_,
        debt2_.fk_status_id as fk_statu5_3_3_,
        debt2_.value as value3_3_3_,
        lessee3_.id as id1_6_4_,
        lessee3_.birth_date as birth_da2_6_4_,
        lessee3_.contact_number as contact_3_6_4_,
        lessee3_.cpf as cpf4_6_4_,
        lessee3_.email as email5_6_4_,
        lessee3_.name as name6_6_4_,
        lessee3_.rg as rg7_6_4_,
        lessee3_.fk_status_id as fk_statu8_6_4_,
        status4_.id as id1_12_5_,
        status4_.name as name2_12_5_,
        status5_.id as id1_12_6_,
        status5_.name as name2_12_6_
    from
        movement movements0_
    left outer join
        activity_type activityty1_
            on movements0_.fk_activity_type_id=activityty1_.id
    left outer join
        debt debt2_
            on movements0_.fk_debt_id=debt2_.id
    left outer join
        lessee lessee3_
            on debt2_.fk_lessee_id=lessee3_.id
    left outer join
        status status4_
            on lessee3_.fk_status_id=status4_.id
    left outer join
        status status5_
            on debt2_.fk_status_id=status5_.id
    where
        movements0_.fk_employee_id=?
Hibernate:
    select
        specialtie0_.employee_id as employee1_5_0_,
        specialtie0_.specialty_id as specialt2_5_0_,
        specialty1_.id as id1_11_1_,
        specialty1_.name as name2_11_1_
    from
        employee_specialty specialtie0_
    inner join
        specialty specialty1_
            on specialtie0_.specialty_id=specialty1_.id
    where
        specialtie0_.employee_id=?
Hibernate:
    select
        movements0_.fk_employee_id as fk_emplo7_9_0_,
        movements0_.id as id1_9_0_,
        movements0_.id as id1_9_1_,
        movements0_.fk_activity_type_id as fk_activ5_9_1_,
        movements0_.fk_debt_id as fk_debt_6_9_1_,
        movements0_.due_date as due_date2_9_1_,
        movements0_.fk_employee_id as fk_emplo7_9_1_,
        movements0_.move_date_and_time as move_dat3_9_1_,
        movements0_.previous_value as previous4_9_1_,
        activityty1_.id as id1_0_2_,
        activityty1_.name as name2_0_2_,
        debt2_.id as id1_3_3_,
        debt2_.due_date as due_date2_3_3_,
        debt2_.fk_lessee_id as fk_lesse4_3_3_,
        debt2_.fk_status_id as fk_statu5_3_3_,
        debt2_.value as value3_3_3_,
        lessee3_.id as id1_6_4_,
        lessee3_.birth_date as birth_da2_6_4_,
        lessee3_.contact_number as contact_3_6_4_,
        lessee3_.cpf as cpf4_6_4_,
        lessee3_.email as email5_6_4_,
        lessee3_.name as name6_6_4_,
        lessee3_.rg as rg7_6_4_,
        lessee3_.fk_status_id as fk_statu8_6_4_,
        status4_.id as id1_12_5_,
        status4_.name as name2_12_5_,
        status5_.id as id1_12_6_,
        status5_.name as name2_12_6_
    from
        movement movements0_
    left outer join
        activity_type activityty1_
            on movements0_.fk_activity_type_id=activityty1_.id
    left outer join
        debt debt2_
            on movements0_.fk_debt_id=debt2_.id
    left outer join
        lessee lessee3_
            on debt2_.fk_lessee_id=lessee3_.id
    left outer join
        status status4_
            on lessee3_.fk_status_id=status4_.id
    left outer join
        status status5_
            on debt2_.fk_status_id=status5_.id
    where
        movements0_.fk_employee_id=?
2022-01-22 19:49:40.466  INFO 33508 --- [nio-8080-exec-2] system.gc.services.EmployeeService       : Listando funcionários
Hibernate:
    select
        employee0_.id as id1_4_,
        employee0_.birth_date as birth_da2_4_,
        employee0_.cpf as cpf3_4_,
        employee0_.email as email4_4_,
        employee0_.hiring_date as hiring_d5_4_,
        employee0_.name as name6_4_,
        employee0_.rg as rg7_4_,
        employee0_.fk_role_id as fk_role_8_4_,
        employee0_.fk_status_id as fk_statu9_4_
    from
        employee employee0_ limit ?
Hibernate:
    select
        role0_.id as id1_10_0_,
        role0_.name as name2_10_0_
    from
        role role0_
    where
        role0_.id=?
Hibernate:
    select
        status0_.id as id1_12_0_,
        status0_.name as name2_12_0_
    from
        status status0_
    where
        status0_.id=?
Hibernate:
    select
        specialtie0_.employee_id as employee1_5_0_,
        specialtie0_.specialty_id as specialt2_5_0_,
        specialty1_.id as id1_11_1_,
        specialty1_.name as name2_11_1_
    from
        employee_specialty specialtie0_
    inner join
        specialty specialty1_
            on specialtie0_.specialty_id=specialty1_.id
    where
        specialtie0_.employee_id=?
Hibernate:
    select
        movements0_.fk_employee_id as fk_emplo7_9_0_,
        movements0_.id as id1_9_0_,
        movements0_.id as id1_9_1_,
        movements0_.fk_activity_type_id as fk_activ5_9_1_,
        movements0_.fk_debt_id as fk_debt_6_9_1_,
        movements0_.due_date as due_date2_9_1_,
        movements0_.fk_employee_id as fk_emplo7_9_1_,
        movements0_.move_date_and_time as move_dat3_9_1_,
        movements0_.previous_value as previous4_9_1_,
        activityty1_.id as id1_0_2_,
        activityty1_.name as name2_0_2_,
        debt2_.id as id1_3_3_,
        debt2_.due_date as due_date2_3_3_,
        debt2_.fk_lessee_id as fk_lesse4_3_3_,
        debt2_.fk_status_id as fk_statu5_3_3_,
        debt2_.value as value3_3_3_,
        lessee3_.id as id1_6_4_,
        lessee3_.birth_date as birth_da2_6_4_,
        lessee3_.contact_number as contact_3_6_4_,
        lessee3_.cpf as cpf4_6_4_,
        lessee3_.email as email5_6_4_,
        lessee3_.name as name6_6_4_,
        lessee3_.rg as rg7_6_4_,
        lessee3_.fk_status_id as fk_statu8_6_4_,
        status4_.id as id1_12_5_,
        status4_.name as name2_12_5_,
        status5_.id as id1_12_6_,
        status5_.name as name2_12_6_
    from
        movement movements0_
    left outer join
        activity_type activityty1_
            on movements0_.fk_activity_type_id=activityty1_.id
    left outer join
        debt debt2_
            on movements0_.fk_debt_id=debt2_.id
    left outer join
        lessee lessee3_
            on debt2_.fk_lessee_id=lessee3_.id
    left outer join
        status status4_
            on lessee3_.fk_status_id=status4_.id
    left outer join
        status status5_
            on debt2_.fk_status_id=status5_.id
    where
        movements0_.fk_employee_id=?
Hibernate:
    select
        specialtie0_.employee_id as employee1_5_0_,
        specialtie0_.specialty_id as specialt2_5_0_,
        specialty1_.id as id1_11_1_,
        specialty1_.name as name2_11_1_
    from
        employee_specialty specialtie0_
    inner join
        specialty specialty1_
            on specialtie0_.specialty_id=specialty1_.id
    where
        specialtie0_.employee_id=?
Hibernate:
    select
        movements0_.fk_employee_id as fk_emplo7_9_0_,
        movements0_.id as id1_9_0_,
        movements0_.id as id1_9_1_,
        movements0_.fk_activity_type_id as fk_activ5_9_1_,
        movements0_.fk_debt_id as fk_debt_6_9_1_,
        movements0_.due_date as due_date2_9_1_,
        movements0_.fk_employee_id as fk_emplo7_9_1_,
        movements0_.move_date_and_time as move_dat3_9_1_,
        movements0_.previous_value as previous4_9_1_,
        activityty1_.id as id1_0_2_,
        activityty1_.name as name2_0_2_,
        debt2_.id as id1_3_3_,
        debt2_.due_date as due_date2_3_3_,
        debt2_.fk_lessee_id as fk_lesse4_3_3_,
        debt2_.fk_status_id as fk_statu5_3_3_,
        debt2_.value as value3_3_3_,
        lessee3_.id as id1_6_4_,
        lessee3_.birth_date as birth_da2_6_4_,
        lessee3_.contact_number as contact_3_6_4_,
        lessee3_.cpf as cpf4_6_4_,
        lessee3_.email as email5_6_4_,
        lessee3_.name as name6_6_4_,
        lessee3_.rg as rg7_6_4_,
        lessee3_.fk_status_id as fk_statu8_6_4_,
        status4_.id as id1_12_5_,
        status4_.name as name2_12_5_,
        status5_.id as id1_12_6_,
        status5_.name as name2_12_6_
    from
        movement movements0_
    left outer join
        activity_type activityty1_
            on movements0_.fk_activity_type_id=activityty1_.id
    left outer join
        debt debt2_
            on movements0_.fk_debt_id=debt2_.id
    left outer join
        lessee lessee3_
            on debt2_.fk_lessee_id=lessee3_.id
    left outer join
        status status4_
            on lessee3_.fk_status_id=status4_.id
    left outer join
        status status5_
            on debt2_.fk_status_id=status5_.id
    where
        movements0_.fk_employee_id=?
* */