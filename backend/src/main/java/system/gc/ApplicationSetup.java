package system.gc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import system.gc.dtos.*;
import system.gc.entities.*;
import system.gc.repositories.RoleRepository;
import system.gc.repositories.StatusRepository;
import system.gc.repositories.TypeProblemRepository;
import system.gc.security.EmployeeUserDetails;
import system.gc.services.ServiceImpl.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.time.DayOfWeek.*;

@Component
@Slf4j
public class ApplicationSetup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    RoleService roleService;

    @Autowired
    ActivityTypeService activityTypeService;

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
    TypeProblemRepository typeProblemRepository;
    @Autowired
    TypeProblemService typeProblemService;

    @Autowired
    RepairRequestService repairRequestService;

    @Autowired
    OrderServiceService orderServiceService;

    @Autowired
    Environment environment;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(today.getDayOfWeek() == FRIDAY ? 2 : 1 );
    LocalDate sixMonths = today.plusMonths(6);

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!Arrays.stream(environment.getActiveProfiles()).toList().contains("test")) return;
        // ROLE
        List<Role> roleList = new ArrayList<>();
        Role roleADM = roleRepository.save(new Role("Administrador"));
        Role roleCounter = roleRepository.save(new Role("Contador"));
        Role roleAssistant = roleRepository.save(new Role("Assistente administrativo"));
        Role electrician = roleRepository.save(new Role("Eletricista"));
        Role plumber = roleRepository.save(new Role("Encanador"));
        Role generalServices = roleRepository.save(new Role("Serviços gerais"));
        //roleService.save(roleList);

        // Status
        List<Status> statusList = new ArrayList<>();
        Status statusActive = statusRepository.save(new Status("Ativo"));
        statusList.add(new Status("Inativo"));
        Status statusOpen = statusRepository.save(new Status("Aberto"));
        statusList.add(new Status("Em andamento"));
        statusList.add(new Status("Desativado"));
        statusList.add(new Status("Aguardando"));
        statusList.add(new Status("Valido"));
        statusList.add(new Status("Invalido"));
        statusList.add(new Status("Cancelado"));
        statusList.add(new Status("Resgatado"));
        statusList.add(new Status("Concluído"));
        statusList.add(new Status("Deletado"));
        Status statusAvailable = statusRepository.save(new Status("Disponível"));
        statusList.add(new Status("Indisponível"));
        statusList.add(new Status("Lotado"));
        statusList.add(new Status("Encerrado"));
        statusList.add(new Status("Expirado"));
        statusList.add(new Status("Vencido"));
        statusList.add(new Status("Atrasado"));
        statusList.add(new Status("Pago"));
        statusService.save(statusList);

        // ACTIVITY TYPE
        List<ActivityType> activityTypeList = new ArrayList<>();
        activityTypeList.add(new ActivityType("Registrado"));
        activityTypeList.add(new ActivityType("Atualizado"));
        activityTypeList.add(new ActivityType("Desativado"));
        activityTypeList.add(new ActivityType("Deletado"));
        activityTypeService.save(activityTypeList);

        List<TypeProblem> typeProblemList = new ArrayList<>();
        TypeProblem typeProblemDTOElectric = typeProblemRepository.save(new TypeProblem("Elétrico"));
        typeProblemList.add(new TypeProblem("Hidráulico"));
        typeProblemList.add(new TypeProblem("Outros"));
        typeProblemService.save(typeProblemList);

        try
        {
            // EMPLOYEE
            EmployeeDTO employeeDTOWisley = employeeService.save(new EmployeeDTO("Wisley Bruno Marques França",
                    "2343435",
                    "12345678910",
                    simpleDateFormat.parse("1995-12-06"),
                    "srmarquesms@gmail.com",
                    simpleDateFormat.parse("2022-01-01"),
                    "admin",
                    new RoleDTO(roleADM),
                    null,
                    new StatusDTO(statusActive)));
            Employee auth = employeeService.authentication("12345678910");
            UserDetails userDetails = new EmployeeUserDetails(auth);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            initializeEmployee(
                    "Eliza Maciel",
                    "6658578",
                    "62578672380",
                    "2000-04-07",
                    "eliza.exemple@gmail.com",
                    "2022-01-04",
                    "eliza123",
                    roleCounter,
                    null,
                    statusActive);

            initializeEmployee(
                    "Amanda Silva",
                    "695854",
                    "12578678980",
                    "2000-12-07",
                    "amanda.exemple@gmail.com",
                    "2022-01-04",
                    "amanda123",
                    roleAssistant,
                    null,
                    statusActive);

            initializeEmployee(
                    "Rafael Almeida",
                    "6958534",
                    "12578678342",
                    "2000-12-08",
                    "rafael.exemple@gmail.com",
                    "2022-01-04",
                    "rafael123",
                    electrician,
                    null,
                    statusActive);

            initializeEmployee(
                    "Antonio Junior",
                    "3951534",
                    "72558678342",
                    "2000-09-08",
                    "antonio.exemple@gmail.com",
                    "2022-01-04",
                    "antonio123",
                    plumber,
                    null,
                    statusActive);

            initializeEmployee(
                    "Jeferson da Silva",
                    "59678534",
                    "90358178342",
                    "2000-01-08",
                    "jeferson.exemple@gmail.com",
                    "2022-01-04",
                    "jeferson123",
                    generalServices,
                    null,
                    statusActive);

            //LOCALIZATION
            LocalizationDTO localizationDTO = localizationService.save(new LocalizationDTO("Flores", "920", 69058200));
            LocalizationDTO localizationDTO1 = localizationService.save(new LocalizationDTO("Parque 10 de Novembro", "Dom Diogo de souza",  69054641));

            // CONDOMINIUM
            CondominiumDTO condominiumDTO = new CondominiumDTO("Villa Lobos",
                    "Desc-1",
                    20,
                    new StatusDTO(statusAvailable),
                    new LocalizationCondominiumDTO("500", localizationDTO));
            CondominiumDTO condominiumDTO2Saved = condominiumService.save(condominiumDTO);
            for (int i = 2; i < 8; i++) {
                initializeCondominium(
                        "CONDOMÍNIO-" + i,
                        "Desc-" + i,
                        6 + i,
                        statusAvailable,
                        new LocalizationCondominiumDTO("900" + i, localizationDTO1)
                );
            }

            // LESSEE
            LesseeDTO lesseeDTODEV = new LesseeDTO(
                    "Rafael da Silva Monteiro",
                    "63598623",
                    "12563256347",
                    simpleDateFormat.parse("2003-06-02"),
                    "brmarques.dev@gmail.com",
                    "92991071491",
                    "dev123456789",
                    new StatusDTO(statusActive)
            );

            LesseeDTO lesseeDTODEVSave = lesseeService.save(lesseeDTODEV);
            for (int i = 0; i < 7; i++) {
                initializeLessee(
                        "Locatário " + i,
                        "635986" + i,
                        "1256325667" + i,
                        "2003-06-02",
                        String.format("example-%d@gmail.com", i),
                        "9298863526" + i,
                        "785452545" + i,
                        statusActive
                );
            }

            Page<CondominiumDTO> condominiumDTOPage = condominiumService.listPaginationCondominium(PageRequest.of(0, 5));
            Page<LesseeDTO> lesseeDTOPage = lesseeService.listPaginationLessees(PageRequest.of(0, 5));

            // CONTRACT
            initializeContract(
                    1200.00,
                    5,
                    10,
                    60,
                    statusOpen,
                    condominiumDTOPage.toList().get(1),
                    lesseeDTOPage.toList().get(1));

            initializeContract(
                    1100.00,
                    10,
                    15,
                    10,
                    statusOpen,
                    condominiumDTOPage.toList().get(1),
                    lesseeDTOPage.toList().get(1));

            // DEBTS
            initializeDebt(1200.00, statusOpen, lesseeDTOPage.toList().get(1));

            // REPAIR REQUESTS
            RepairRequestDTO repairRequestDTOSaved = initializeRepairRequests("Troca de fios eletricos",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeDTODEVSave,
                    condominiumDTO2Saved,
                    "10",
                    statusOpen);

            initializeRepairRequests("Problema de energia",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeDTODEVSave,
                    condominiumDTO2Saved,
                    "08",
                    statusOpen);

            initializeRepairRequests("Problema nas tomadas",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeDTODEVSave,
                    condominiumDTO2Saved,
                    "30",
                    statusOpen);

            // ORDER SERVICE
            initializeOrderService(repairRequestDTOSaved,
                    employeeDTOWisley,
                    statusOpen);


        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());

        }

    }

    public void initializeEmployee(
            String name,
            String rg,
            String cpf,
            String birthDate,
            String email,
            String hiringDate,
            String password,
            Role role,
            Set<MovementDTO> movements,
            Status status
    ) throws ParseException {
        EmployeeDTO employeeDTO = new EmployeeDTO(
                name,
                rg,
                cpf,
                simpleDateFormat.parse(birthDate),
                email,
                simpleDateFormat.parse(hiringDate),
                password,
                new RoleDTO(role),
                movements,
                new StatusDTO(status));
        employeeService.save(employeeDTO);
    }

    public void initializeCondominium(
            String name,
            String description,
            int numberApartments,
            Status status,
            LocalizationCondominiumDTO localization
    )
    {
        CondominiumDTO condominiumDTO = new CondominiumDTO(
                name,
                description,
                numberApartments,
                new StatusDTO(status),
                localization);
        condominiumService.save(condominiumDTO);
    }

    public void initializeLessee(
            String name,
            String rg,
            String cpf,
            String birthDate,
            String email,
            String contactNumber,
            String password,
            Status status
    ) throws ParseException {
        LesseeDTO lesseeDTO = new LesseeDTO(
                name,
                rg,
                cpf,
                simpleDateFormat.parse(birthDate),
                email,
                contactNumber,
                password,
                new StatusDTO(status)
        );
        lesseeService.save(lesseeDTO);
    }

    public void initializeContract(
            double contractValue,
            int monthlyPaymentDate,
            int monthlyDueDate,
            int apartmentNumber,
            Status status,
            CondominiumDTO condominium,
            LesseeDTO lessee) throws ParseException {
        ContractDTO contractDTO = new ContractDTO(simpleDateFormat.parse(tomorrow.toString()),
                contractValue,
                monthlyPaymentDate,
                monthlyDueDate,
                simpleDateFormat.parse(sixMonths.toString()),
                apartmentNumber,
                new StatusDTO(status),
                condominium,
                lessee);
        contractService.save(contractDTO);
    }

    public void initializeDebt(double value, Status status, LesseeDTO lesseeDTO) throws ParseException {
        // Prazo de validade
        int days = getDueDate(1, 7, today);
        DebtDTO debtDTO = new DebtDTO(simpleDateFormat.parse(today.plusDays(days).toString()),
                value,
                new StatusDTO().toDTO(status),
                null,
                lesseeDTO);
        debtService.save(debtDTO);
    }

    /**
     *
     * @param quantityDays  // Quantidade de dias contanto a parti do dia de amanhã
     * @param length       // Quantidade em dias úteis.
     * @param today        // Data atual
     * @return             // quantidade de dias necessário para o prazo de validade
     */
    public static int getDueDate(int quantityDays, int length, LocalDate today)
    {
        while(quantityDays <= length) {
            DayOfWeek current = today.plusDays(quantityDays).getDayOfWeek();
            if (current == SATURDAY || current == SUNDAY)
            {
                length++;
            }
            if (quantityDays == length)
            {
                break;
            }
            quantityDays++;
        }

        return quantityDays;
    }

    public RepairRequestDTO initializeRepairRequests(
            String problemDescription,
            TypeProblemDTO typeProblemDTO,
            LesseeDTO lesseeDTO,
            CondominiumDTO condominiumDTO,
            String apartmentNumber,
            Status status) throws ParseException {
        RepairRequestDTO repairRequestDTO = new RepairRequestDTO(problemDescription,
                simpleDateFormat.parse(today.toString()),
                typeProblemDTO,
                lesseeDTO,
                condominiumDTO,
                apartmentNumber,
                new StatusDTO(status));

        return repairRequestService.save(repairRequestDTO);
    }

    public void initializeOrderService(
            RepairRequestDTO repairRequestDTO,
            EmployeeDTO employeeDTO,
            Status status) throws ParseException {
        OrderServiceDTO orderServiceDTO = new OrderServiceDTO(
                simpleDateFormat.parse(today.toString()),
                simpleDateFormat.parse(tomorrow.toString()),
                Set.of(repairRequestDTO),
                Set.of(employeeDTO),
                new StatusDTO(status)
        );
        orderServiceService.save(orderServiceDTO);
    }
}