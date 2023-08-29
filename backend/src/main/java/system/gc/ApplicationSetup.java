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
import system.gc.exceptionsAdvice.exceptions.DebtNotCreatedException;
import system.gc.repositories.RoleRepository;
import system.gc.repositories.StatusRepository;
import system.gc.repositories.TypeProblemRepository;
import system.gc.security.EmployeeUserDetails;
import system.gc.services.web.impl.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import static java.time.DayOfWeek.*;
import static system.gc.utils.TextUtils.*;

@Component
@Slf4j
public class ApplicationSetup {

    @Autowired
    WebEmployeeService webEmployeeService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    WebStatusService webStatusService;

    @Autowired
    WebActivityTypeService webActivityTypeService;

    @Autowired
    WebLocalizationService webLocalizationService;

    @Autowired
    WebCondominiumService webCondominiumService;

    @Autowired
    WebContractService webContractService;

    @Autowired
    WebLesseeService webLesseeService;

    @Autowired
    WebDebtService webDebtService;

    @Autowired
    TypeProblemRepository typeProblemRepository;

    @Autowired
    WebTypeProblemService webTypeProblemService;

    @Autowired
    WebRepairRequestService webRepairRequestService;

    @Autowired
    WebOrderServiceService webOrderServiceService;

    @Autowired
    private WebDataReloadService webDataReloadService;

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
        Role roleADM = roleRepository.save(new Role(ROLE_ADMINISTRATOR));
        Role roleAssistant = roleRepository.save(new Role(ROLE_ADMINISTRATIVE_ASSISTANT));
        Role roleCounter = roleRepository.save(new Role(ROLE_COUNTER));
        Role electrician = roleRepository.save(new Role(ROLE_ELECTRICIAN));
        Role plumber = roleRepository.save(new Role(ROLE_PLUMBER));
        Role generalServices = roleRepository.save(new Role(ROLE_GENERAL_SERVICES));
        Role lesseeRole = roleRepository.save(new Role(ROLE_LESSEE));

        // Status
        List<Status> statusList = new ArrayList<>();
        Status statusActive = statusRepository.save(new Status(STATUS_ACTIVE));
        statusList.add(new Status(STATUS_INACTIVE));
        Status statusOpen = statusRepository.save(new Status(STATUS_OPEN));
        statusList.add(new Status(STATUS_IN_PROGRESS));
        statusList.add(new Status(STATUS_DISABLED));
        statusList.add(new Status(STATUS_WAITING));
        statusList.add(new Status(STATUS_VALID));
        statusList.add(new Status(STATUS_INVALID));
        statusList.add(new Status(STATUS_CANCELED));
        statusList.add(new Status(STATUS_RESCUED));
        statusList.add(new Status(STATUS_CONCLUDED));
        statusList.add(new Status(STATUS_DELETED));
        Status statusAvailable = statusRepository.save(new Status(STATUS_AVAILABLE));
        statusList.add(new Status(STATUS_UNAVAILABLE));
        statusList.add(new Status(STATUS_CROWDED));
        statusList.add(new Status(STATUS_CLOSED));
        statusList.add(new Status(STATUS_EXPIRED));
        statusList.add(new Status(STATUS_OVERDUE));
        statusList.add(new Status(STATUS_LATE));
        statusList.add(new Status(STATUS_PAID));
        webStatusService.save(statusList);

        // ACTIVITY TYPE
        List<ActivityType> activityTypeList = new ArrayList<>();
        activityTypeList.add(new ActivityType(ACTIVITY_TYPE_REGISTERED));
        activityTypeList.add(new ActivityType(ACTIVITY_TYPE_UPDATED));
        activityTypeList.add(new ActivityType(ACTIVITY_TYPE_DISABLED));
        activityTypeList.add(new ActivityType(ACTIVITY_TYPE_DELETED));
        webActivityTypeService.save(activityTypeList);

        List<TypeProblem> typeProblemList = new ArrayList<>();
        TypeProblem typeProblemDTOElectric = typeProblemRepository.save(new TypeProblem(TYPE_PROBLEM_ELECTRIC));
        typeProblemList.add(new TypeProblem(TYPE_PROBLEM_HYDRAULIC));
        typeProblemList.add(new TypeProblem(TYPE_PROBLEM_OTHERS));
        webTypeProblemService.save(typeProblemList);

        try
        {
            // EMPLOYEE
            webEmployeeService.save(new EmployeeDTO("Wisley Bruno Marques França",
                    "2343435",
                    "12345678910",
                    Date.from(ZonedDateTime.of(1995, 12, 6, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "srwisley.dev@gmail.com",
                    Date.from(ZonedDateTime.of(2022, 1, 1, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "admin",
                    new RoleDTO(roleADM),
                    null,
                    new StatusDTO(statusActive)));
            Employee auth = webEmployeeService.authentication("12345678910");
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

            EmployeeDTO employeeElectrician2 = initializeEmployee(
                    "Henrique",
                    "69538534",
                    "12578678142",
                    Date.from(ZonedDateTime.of(2000, 12, 8, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "henrique.exemple@gmail.com",
                    hiringDate,
                    "henrique123",
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
            LocalizationDTO localizationDTO = webLocalizationService.save(new LocalizationDTO("Flores", "Rua Barão de Palmares", 69058200));
            LocalizationDTO localizationDTO1 = webLocalizationService.save(new LocalizationDTO("Parque 10 de Novembro", "Rua Dom Diogo de souza",  69054641));

            // CONDOMINIUM
            CondominiumDTO condominiumDTO = new CondominiumDTO("Villa Lobos",
                    "Desc-1",
                    20,
                    new StatusDTO(statusAvailable),
                    new LocalizationCondominiumDTO("500", localizationDTO));
            webCondominiumService.save(condominiumDTO);
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
            LesseeDTO lesseeDTO = new LesseeDTO(
                    "Francisco da Silva",
                    "63598623",
                    "12345678909",
                    Date.from(ZonedDateTime.of(2003, 6, 2, 8, 0, 0, 0, zoneManaus).toInstant()),
                    "brmarques.dev@gmail.com",
                    "92941571491",
                    "francisco123",
                    new StatusDTO(statusActive),
                    new RoleDTO(lesseeRole)
            );
            LesseeDTO lesseeSaved = webLesseeService.save(lesseeDTO);
            Page<CondominiumDTO> condominiumDTOPage = webCondominiumService.listPaginationCondominium(PageRequest.of(0, 5));

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
                    lesseeSaved);

            initializeContract(
                    oneMonthAgo,
                    5000.00,
                    today.getDayOfMonth(),
                    today.plusDays(5).getDayOfMonth(),
                    oneMonthAgo.plusMonths(6),
                    10,
                    statusActive,
                    condominiumDTOPage.toList().get(1),
                    lesseeSaved);

            // DEBTS
            Page<ContractDTO> contractDTOPage = webContractService.searchContract(PageRequest.of(0, 5), lesseeSaved);
            ContractDTO contractDTO = contractDTOPage.toList().get(0);
            initializeDebt(contractDTO.getContractValue(), statusOpen, lesseeSaved, today);

            // REPAIR REQUESTS
            RepairRequestDTO repairRequestDTOSaved = initializeRepairRequests("Troca de fios eletricos",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            RepairRequestDTO repairRequestDTOSaved2 = initializeRepairRequests("Troca de fios eletricos 2",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            RepairRequestDTO repairRequestDTOSaved3 = initializeRepairRequests("Troca de fios eletricos 2",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            RepairRequestDTO repairRequestDTOSaved4 = initializeRepairRequests("Troca de fios eletricos 2",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            RepairRequestDTO repairRequestDTOSaved5 = initializeRepairRequests("Troca de fios eletricos 2",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            RepairRequestDTO repairRequestDTOSaved6 = initializeRepairRequests("Troca de fios eletricos 2",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            initializeRepairRequests("Problema de energia",
                    new TypeProblemDTO(typeProblemDTOElectric),
                    lesseeSaved,
                    condominiumDTOPage.toList().get(2),
                    "10",
                    statusOpen);

            // ORDER SERVICE
            initializeOrderService(repairRequestDTOSaved,
                    Set.of(employeeElectrician, employeeElectrician2),
                    statusOpen);

            initializeOrderService(repairRequestDTOSaved2,
                    Set.of(employeeElectrician2),
                    statusActive);

            initializeOrderService(repairRequestDTOSaved3,
                    Set.of(employeeElectrician),
                    statusOpen);

            initializeOrderService(repairRequestDTOSaved4,
                    Set.of(employeeElectrician),
                    statusOpen);

            initializeOrderService(repairRequestDTOSaved5,
                    Set.of(employeeElectrician),
                    statusOpen);

            initializeOrderService(repairRequestDTOSaved6,
                    Set.of(employeeElectrician),
                    statusActive);
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
        webDataReloadService.deleteAll();
        execute();
    }

    @Bean
    public void initDataProfileProd()
    {
        if (!Arrays.stream(environment.getActiveProfiles()).toList().contains("production")) return;
        webDataReloadService.deleteAll();
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
        return webEmployeeService.save(employeeDTO);
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
        webCondominiumService.save(condominiumDTO);
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
        webContractService.save(contractDTO);
    }

    private void initializeDebt(double value, Status status, LesseeDTO lesseeDTO, ZonedDateTime openDate) throws ParseException, DebtNotCreatedException {
        // Prazo de validade
        int days = getDueDate(1, 7, openDate);
        DebtDTO debtDTO = new DebtDTO(simpleDateFormat.parse(openDate.plusDays(days).toString()),
                value,
                new StatusDTO().toDTO(status),
                null,
                lesseeDTO);
        webDebtService.save(debtDTO);
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
        return webRepairRequestService.save(repairRequestDTO);
    }

    private void initializeOrderService(
            RepairRequestDTO repairRequestDTO,
            Set<EmployeeDTO> employeeDTO,
            Status status) throws ParseException {
        OrderServiceDTO orderServiceDTO = new OrderServiceDTO(
                simpleDateFormat.parse(today.toString()),
                simpleDateFormat.parse(tomorrow.toString()),
                Set.of(repairRequestDTO),
                employeeDTO,
                new StatusDTO(status)
        );
        webOrderServiceService.save(orderServiceDTO);
    }
}