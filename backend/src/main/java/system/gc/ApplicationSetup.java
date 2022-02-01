package system.gc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import system.gc.dtos.*;
import system.gc.entities.Role;
import system.gc.entities.Specialty;
import system.gc.entities.Status;
import system.gc.repositories.RoleRepository;
import system.gc.repositories.SpecialtyRepository;
import system.gc.repositories.StatusRepository;
import system.gc.services.CondominiumService;
import system.gc.services.EmployeeService;
import system.gc.services.LocalizationService;

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
    LocalizationService localizationService;

    @Autowired
    CondominiumService condominiumService;

    @Autowired
    private MessageSource messageSource;

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
        try {
            employeeService.save(new EmployeeDTO("Wisley Bruno Marques França",
                    "2343435",
                    "1234567898",
                    simpleDateFormat.parse("1995-12-06"),
                    "srmarquesms@gmail.com",
                    new Date(),
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
                    new RoleDTO(roles.get(0)),
                    spEmployee,
                    null,
                    new StatusDTO(status.get(0))));

            LocalizationDTO localizationDTO = localizationService.save(new LocalizationDTO("Flores", "920", "69058200"));

            CondominiumDTO condominiumDTO = new CondominiumDTO("Villa Lobos",
                    "A30",
                    20,
                    new StatusDTO(status.get(0)),
                    new LocalizationCondominiumDTO("500", localizationDTO));

            condominiumService.save(condominiumDTO);
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
                    new StatusDTO(status.get(0))));  */
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