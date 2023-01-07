package system.gc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static java.time.DayOfWeek.*;

@Component
@Slf4j
public class ApplicationSetup {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    StatusService statusService;

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
    private DataReloadService dataReloadService;

    @Autowired
    Environment environment;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String TIME_ZONE = "America/Manaus";

    /**
     *
     */
    private static final ZoneId zoneManaus = ZoneId.of(TIME_ZONE);
    ZonedDateTime today = ZonedDateTime.now(ZoneId.of(TIME_ZONE));
    ZonedDateTime tomorrow = today.plusDays(today.getDayOfWeek() == FRIDAY ? 2 : 1 );

    ZonedDateTime oneMonthAgo = today.minusDays(30);

    Date hiringDate = Date.from(ZonedDateTime.of(2022, 1, 4, 8, 0, 0, 0, zoneManaus).toInstant());

    @SneakyThrows
    @Transactional
    public void execute()
    {
        log.info("Inserindo dados");
        // ROLE
        //List<Role> roleList = new ArrayList<>();
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
            employeeService.save(new EmployeeDTO("Wisley Bruno Marques França",
                    "2343435",
                    "12345678910",
                    Date.from(ZonedDateTime.of(1995, 12, 6, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "srmarquesms@gmail.com",
                    Date.from(ZonedDateTime.of(2022, 1, 1, 8, 0, 0, 0, zoneManaus).toInstant()),
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
                    Date.from(ZonedDateTime.of(2000, 4, 7, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "eliza.exemple@gmail.com",
                    hiringDate,
                    "eliza123",
                    roleCounter,
                    null,
                    statusActive);

            initializeEmployee(
                    "Amanda Silva",
                    "695854",
                    "12578678980",
                    Date.from(ZonedDateTime.of(2000, 12, 7, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "amanda.exemple@gmail.com",
                    hiringDate,
                    "amanda123",
                    roleAssistant,
                    null,
                    statusActive);

            EmployeeDTO employeeElectrician = initializeEmployee(
                    "Rafael Almeida",
                    "6958534",
                    "12578678342",
                    Date.from(ZonedDateTime.of(2000, 12, 8, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "rafael.exemple@gmail.com",
                    hiringDate,
                    "rafael123",
                    electrician,
                    null,
                    statusActive);

            initializeEmployee(
                    "Antonio Junior",
                    "3951534",
                    "72558678342",
                    Date.from(ZonedDateTime.of(2000, 9, 8, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "antonio.exemple@gmail.com",
                    hiringDate,
                    "antonio123",
                    plumber,
                    null,
                    statusActive);

            initializeEmployee(
                    "Jeferson da Silva",
                    "59678534",
                    "90358178342",
                    Date.from(ZonedDateTime.of(2000, 1, 8, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "jeferson.exemple@gmail.com",
                    hiringDate,
                    "jeferson123",
                    generalServices,
                    null,
                    statusActive);

            //LOCALIZATION
            LocalizationDTO localizationDTO = localizationService.save(new LocalizationDTO("Flores", "Rua Barão de Palmares", 69058200));
            LocalizationDTO localizationDTO1 = localizationService.save(new LocalizationDTO("Parque 10 de Novembro", "Rua Dom Diogo de souza",  69054641));

            // CONDOMINIUM
            CondominiumDTO condominiumDTO = new CondominiumDTO("Villa Lobos",
                    "Desc-1",
                    20,
                    new StatusDTO(statusAvailable),
                    new LocalizationCondominiumDTO("500", localizationDTO));
            condominiumService.save(condominiumDTO);
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
                    Date.from(ZonedDateTime.of(2003, 6, 2, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "brmarques.dev@gmail.com",
                    "92941571491",
                    "rafael123",
                    new StatusDTO(statusActive)
            );

            lesseeService.save(lesseeDTODEV);

            LesseeDTO lesseeDTODEV2 = new LesseeDTO(
                    "Juliana Costa da Silva",
                    "78598423",
                    "45565625634",
                    Date.from(ZonedDateTime.of(1992, 6, 2, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "example-juliana@gmail.com",
                    "92991471431",
                    "juliana123",
                    new StatusDTO(statusActive)
            );

            LesseeDTO lesseeDTODEV2Save = lesseeService.save(lesseeDTODEV2);

            for (int i = 10; i < 35; i++) {
                initializeLessee(
                        "Locatário " + i,
                        "635986" + i,
                        "125632566" + i,
                        Date.from(ZonedDateTime.of(2003, 6, 2, 8, 0, 0, 0, zoneManaus).toInstant()),
                        String.format("example-%d@gmail.com", i),
                        "9298863526" + i,
                        "785452545" + i,
                        statusActive
                );
            }

            Page<CondominiumDTO> condominiumDTOPage = condominiumService.listPaginationCondominium(PageRequest.of(0, 5));

            // CONTRACT
            initializeContract(
                    oneMonthAgo,
                    3000.00,
                    today.getDayOfMonth(),
                    today.plusDays(5).getDayOfMonth(),
                    oneMonthAgo.plusMonths(6),
                    10,
                    statusActive,
                    condominiumDTOPage.toList().get(2),
                    lesseeDTODEV2Save);

            // DEBTS
            Page<ContractDTO> contractDTOPage = contractService.searchContract(PageRequest.of(0, 5), lesseeDTODEV2Save);
            ContractDTO contractDTO = contractDTOPage.toList().get(0);
            initializeDebt(contractDTO.getContractValue(), statusOpen, lesseeDTODEV2Save, today);

            // REPAIR REQUESTS
            RepairRequestDTO repairRequestDTOSaved = initializeRepairRequests("Troca de fios eletricos",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeDTODEV2Save,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            initializeRepairRequests("Problema de energia",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeDTODEV2Save,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            // ORDER SERVICE
            initializeOrderService(repairRequestDTOSaved,
                    employeeElectrician,
                    statusOpen);


        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
    }

    @Bean
    public void initDataProfileTest() {
        if (!Arrays.stream(environment.getActiveProfiles()).toList().contains("test")) return;
        execute();
    }

    @Bean
    public void initDataProfileDev()
    {
        if (!Arrays.stream(environment.getActiveProfiles()).toList().contains("development")) return;
        dataReloadService.deleteAll();
        execute();
    }

    @Bean
    public void initDataProfileProd()
    {
        if (!Arrays.stream(environment.getActiveProfiles()).toList().contains("production")) return;
        dataReloadService.deleteAll();
        execute();
    }

    private EmployeeDTO initializeEmployee(
            String name,
            String rg,
            String cpf,
            Date birthDate,
            String email,
            Date hiringDate,
            String password,
            Role role,
            Set<MovementDTO> movements,
            Status status
    ) throws ParseException {
        EmployeeDTO employeeDTO = new EmployeeDTO(
                name,
                rg,
                cpf,
                birthDate,
                email,
                hiringDate,
                password,
                new RoleDTO(role),
                movements,
                new StatusDTO(status));
        return employeeService.save(employeeDTO);
    }

    private void initializeCondominium(
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

    private void initializeLessee(
            String name,
            String rg,
            String cpf,
            Date birthDate,
            String email,
            String contactNumber,
            String password,
            Status status
    ) throws ParseException {
        LesseeDTO lesseeDTO = new LesseeDTO(
                name,
                rg,
                cpf,
                birthDate,
                email,
                contactNumber,
                password,
                new StatusDTO(status)
        );
        lesseeService.save(lesseeDTO);
    }

    private void initializeContract(
            ZonedDateTime contractDate,
            double contractValue,
            int monthlyPaymentDate,
            int monthlyDueDate,
            ZonedDateTime contractExpirationDate,
            int apartmentNumber,
            Status status,
            CondominiumDTO condominium,
            LesseeDTO lessee) throws ParseException {
        ContractDTO contractDTO = new ContractDTO(simpleDateFormat.parse(contractDate.toString()),
                contractValue,
                monthlyPaymentDate,
                monthlyDueDate,
                simpleDateFormat.parse(contractExpirationDate.toString()),
                apartmentNumber,
                new StatusDTO(status),
                condominium,
                lessee);
        contractService.save(contractDTO);
    }

    private void initializeDebt(double value, Status status, LesseeDTO lesseeDTO, ZonedDateTime openDate) throws ParseException {
        // Prazo de validade
        int days = getDueDate(1, 7, openDate);
        DebtDTO debtDTO = new DebtDTO(simpleDateFormat.parse(openDate.plusDays(days).toString()),
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
    private static int getDueDate(int quantityDays, int length, ZonedDateTime today)
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

    private RepairRequestDTO initializeRepairRequests(
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

    private void initializeOrderService(
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