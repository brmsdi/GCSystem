package system.gc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import system.gc.configuration.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.dtos.*;
import system.gc.entities.OrderService;
import system.gc.entities.RepairRequest;
import system.gc.entities.Status;
import system.gc.matchers.ADDRepairRequestInOrderServiceMatcher;
import system.gc.matchers.RemoveRepairRequestInOrderServiceMatcher;
import system.gc.matchers.RepairRequestStatusMatcher;
import system.gc.repositories.OrderServiceRepository;
import system.gc.repositories.RepairRequestRepository;
import system.gc.repositories.StatusRepository;
import system.gc.services.ServiceImpl.*;
import system.gc.services.testConfigurations.OrderServiceServiceTestConfiguration;
import system.gc.services.testConfigurations.RepairRequestServiceTestConfiguration;
import system.gc.services.testConfigurations.StatusServiceTestConfiguration;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@Import(
        {
            OrderServiceServiceTestConfiguration.class,
            RepairRequestServiceTestConfiguration.class,
            StatusServiceTestConfiguration.class
        })
public class OrderServiceServiceTest {

    @Autowired
    private OrderServiceService orderServiceService;

    private StatusDTO statusDTOOpen;

    private StatusDTO statusDTOConcluded;

    private StatusDTO statusDTOCanceled;

    private StatusDTO statusDTOActive;

    private StatusDTO statusDTOProgress;

    private Date today;

    private Date yesterday;

    private TypeProblemDTO typeProblemDTOOthers;

    private LesseeDTO oneLesseeDTO;

    private CondominiumDTO oneCondominiumDTO;

    private EmployeeDTO oneEmployeeDTO;

    private RepairRequestDTO repairRequestDTO1;

    private RepairRequestDTO repairRequestDTO2;

    private RepairRequestDTO repairRequestDTO3;

    private RepairRequestDTO repairRequestDTO4;

    @MockBean
    private OrderServiceRepository orderServiceRepository;

    @MockBean
    private RepairRequestRepository repairRequestRepository;

    @MockBean
    private StatusRepository statusRepository;

    @BeforeEach
    public void setup() {
        statusDTOOpen = new StatusDTO();
        statusDTOOpen.setId(1);
        statusDTOOpen.setName("Aberto");
        statusDTOConcluded = new StatusDTO();
        statusDTOConcluded.setId(2);
        statusDTOConcluded.setName("Concluído");
        statusDTOCanceled = new StatusDTO();
        statusDTOCanceled.setId(3);
        statusDTOCanceled.setName("Cancelado");
        statusDTOActive = new StatusDTO();
        statusDTOActive.setId(4);
        statusDTOActive.setName("Ativo");
        statusDTOProgress = new StatusDTO();
        statusDTOProgress.setId(5);
        statusDTOProgress.setName("Em andamento");
        typeProblemDTOOthers = new TypeProblemDTO();
        typeProblemDTOOthers.setId(1);
        typeProblemDTOOthers.setName("Outros");
        LocalDate localDate = LocalDate.now();
        today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        yesterday = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        oneLesseeDTO = new LesseeDTO(
                "1 locatário",
                "1234567",
                "12345678910",
                "06/06/1999",
                "locatario@gmail.com",
                "192544448",
                "123456788",
                statusDTOActive);
        oneLesseeDTO.setId(1);

        oneCondominiumDTO = new CondominiumDTO(
                "Condominio 1",
                "Descrição condominio 1",
                1,
                statusDTOActive,
                new LocalizationCondominiumDTO()
        );

        oneEmployeeDTO = new EmployeeDTO(
                "1 Funcionário",
                "19289383",
                "12365895412",
                "06/06/1999",
                "funcionario1@gmail.com",
                today.toString(),
                "526565665",
                new RoleDTO(),
                null,
                statusDTOActive
        );

        repairRequestDTO1 = new RepairRequestDTO(
                "Descrição 1",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "10",
                statusDTOOpen);
        repairRequestDTO1.setId(1);

        repairRequestDTO2 = new RepairRequestDTO(
                "Descrição 2",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "11",
                statusDTOOpen
        );
        repairRequestDTO2.setId(2);

        repairRequestDTO3 = new RepairRequestDTO(
                "Descrição 3",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "13",
                statusDTOOpen
        );
        repairRequestDTO3.setId(3);

        repairRequestDTO4 = new RepairRequestDTO(
                "Descrição 4",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "14",
                statusDTOOpen
        );
        repairRequestDTO4.setId(4);
    }

    @Test
    public void successAddOrderService_test() {
        Set<RepairRequestDTO> newsRepairRequests = Set.of(repairRequestDTO1, repairRequestDTO2);
        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                newsRepairRequests,
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        OrderService savedOrderService = new OrderServiceDTO().toEntity(unsavedOrderServiceDTO);
        savedOrderService.setId(1);
        when(statusRepository.findByName(statusDTOOpen.getName()))
                .thenReturn(Optional.of(new StatusDTO().toEntity(statusDTOOpen)));
        when(repairRequestRepository.checkIfTheRequestIsOpen(Mockito.any(), Mockito.any()))
                .thenReturn(Long.parseLong("" + newsRepairRequests.size()));
        when(orderServiceRepository.save(Mockito.any()))
                .thenReturn(savedOrderService);
        when(statusRepository.findByName(statusDTOProgress.getName()))
                .thenReturn(Optional.of(new StatusDTO().toEntity(statusDTOProgress)));
        OrderServiceDTO saved = orderServiceService.save(unsavedOrderServiceDTO);
        assertThat(saved.getId(), is(savedOrderService.getId()));
    }

    @Test
    public void successCloseOrderService() {
        repairRequestDTO1.setStatus(statusDTOProgress);
        repairRequestDTO2.setStatus(statusDTOProgress);
        OrderServiceDTO savedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        savedOrderServiceDTO.setId(100);
        OrderServiceDTO orderServiceForConclusion = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOConcluded);
        orderServiceForConclusion.setId(100);
        when(orderServiceRepository.findById(savedOrderServiceDTO.getId()))
                .thenReturn(Optional.of(new OrderServiceDTO().toEntity(savedOrderServiceDTO)));
        when(statusRepository.findAllToView(Mockito.any()))
                .thenReturn(List.of(new StatusDTO().toEntity(statusDTOConcluded)));
        when(orderServiceRepository.save(Mockito.any()))
                .thenAnswer(item -> item.getArguments()[0]);
        OrderServiceDTO concludedOrderService = orderServiceService.update(orderServiceForConclusion);
        assertThat(concludedOrderService.getStatus().getId(), is(statusDTOConcluded.getId()));
    }

    @Test
    public void successCancelOrderService_test() {
        repairRequestDTO1.setStatus(statusDTOProgress);
        repairRequestDTO2.setStatus(statusDTOProgress);
        OrderServiceDTO savedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        savedOrderServiceDTO.setId(100);
        OrderServiceDTO orderServiceForCancellation = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOCanceled);
        orderServiceForCancellation.setId(100);
        when(orderServiceRepository.findById(savedOrderServiceDTO.getId()))
                .thenReturn(Optional.of(new OrderServiceDTO().toEntity(savedOrderServiceDTO)));
        when(statusRepository.findAllToView(List.of("Aberto", "Cancelado")))
                .thenReturn(List.of(new Status(statusDTOOpen.getId(), statusDTOOpen.getName()), new Status(statusDTOCanceled.getId(), statusDTOCanceled.getName())));
        when(orderServiceRepository.save(Mockito.any()))
                .thenAnswer(item -> item.getArguments()[0]);
        OrderServiceDTO canceledOrderService = orderServiceService.update(orderServiceForCancellation);
        assertThat(canceledOrderService.getStatus().getId(), is(statusDTOCanceled.getId()));
    }

    @Test
    public void statusRepairRequestNotOpenInNewOrderService_test() {
        repairRequestDTO1.setStatus(statusDTOProgress);
        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        when(statusRepository.findByName(statusDTOOpen.getName()))
                .thenReturn(Optional.of(new StatusDTO().toEntity(statusDTOOpen)));
        try {
            orderServiceService.save(unsavedOrderServiceDTO);
            fail("Deveria lançar uma IllegalSelectedRepairRequestsException");
        } catch (IllegalSelectedRepairRequestsException error)
        {
            assertThat(error.getMessage(), is("Solicitações de reparo com o status invalido. Selecione as solicitações com o status 'Aberto'"));
        }
    }

    @Test
    public void orderServiceNotFoundForUpdate_test() {
        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        unsavedOrderServiceDTO.setId(500);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> orderServiceService.update(unsavedOrderServiceDTO));
        assertThat(exception.getMessage(), is("Registro não encontrado"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void statusConcludedInRepairRequestsInOrderService_test() {
        repairRequestDTO1.setStatus(statusDTOProgress);
        repairRequestDTO2.setStatus(statusDTOProgress);
        OrderServiceDTO savedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        savedOrderServiceDTO.setId(100);
        OrderServiceDTO orderServiceForConclusion = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOConcluded);
        orderServiceForConclusion.setId(100);
        when(orderServiceRepository.findById(savedOrderServiceDTO.getId()))
                .thenReturn(Optional.of(new OrderServiceDTO().toEntity(savedOrderServiceDTO)));
        when(statusRepository.findAllToView(Mockito.any()))
                .thenReturn(List.of(new StatusDTO().toEntity(statusDTOConcluded)));
        when(orderServiceRepository.save(Mockito.any()))
                .thenAnswer(item -> item.getArguments()[0]);
        AtomicReference<List<RepairRequest>> listRepairRequest = new AtomicReference<>(new ArrayList<>());
        when(repairRequestRepository.saveAll(Mockito.any()))
                .thenAnswer(item -> {
                    Set<RepairRequest> repairRequestSet = (Set<RepairRequest>) item.getArguments()[0];
                    listRepairRequest.get().addAll(repairRequestSet.stream().toList());
                    return listRepairRequest.get();
                });
        orderServiceService.update(orderServiceForConclusion);
        assertThat(listRepairRequest.get().stream().map(repairRequest -> new StatusDTO(repairRequest.getStatus())).collect(Collectors.toSet()),
                new RepairRequestStatusMatcher(statusDTOConcluded));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void statusCanceledInRepairRequestsInOrderService_test() {
        repairRequestDTO1.setStatus(statusDTOProgress);
        repairRequestDTO2.setStatus(statusDTOProgress);
        OrderServiceDTO savedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        savedOrderServiceDTO.setId(100);
        OrderServiceDTO orderServiceForCancellation = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOCanceled);
        orderServiceForCancellation.setId(100);
        when(orderServiceRepository.findById(savedOrderServiceDTO.getId()))
                .thenReturn(Optional.of(new OrderServiceDTO().toEntity(savedOrderServiceDTO)));
        when(statusRepository.findAllToView(List.of("Aberto", "Cancelado")))
                .thenReturn(List.of(new Status(statusDTOOpen.getId(), statusDTOOpen.getName()), new Status(statusDTOCanceled.getId(), statusDTOCanceled.getName())));
        when(orderServiceRepository.save(Mockito.any()))
                .thenAnswer(item -> item.getArguments()[0]);
        AtomicReference<List<RepairRequest>> listRepairRequest = new AtomicReference<>(new ArrayList<>());
        when(repairRequestRepository.saveAll(Mockito.any()))
                .thenAnswer(item -> {
                    Set<RepairRequest> repairRequestSet = (Set<RepairRequest>) item.getArguments()[0];
                    listRepairRequest.get().addAll(repairRequestSet.stream().toList());
                    return listRepairRequest.get();
                });
        orderServiceService.update(orderServiceForCancellation);
        assertThat(listRepairRequest.get().stream().map(repairRequest -> new StatusDTO(repairRequest.getStatus())).collect(Collectors.toSet()),
                new RepairRequestStatusMatcher(statusDTOOpen));
    }

    @Test
    public void addRepairRequestInOrderService_test()
    {
        repairRequestDTO1.setStatus(statusDTOProgress);
        repairRequestDTO2.setStatus(statusDTOProgress);
        OrderServiceDTO savedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        savedOrderServiceDTO.setId(100);
        OrderServiceDTO updateOrderService = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2, repairRequestDTO3),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        updateOrderService.setId(100);
        when(orderServiceRepository.findById(savedOrderServiceDTO.getId()))
                .thenReturn(Optional.of(new OrderServiceDTO().toEntity(savedOrderServiceDTO)));
        when(statusRepository.findAllToView(List.of("Aberto", "Em andamento")))
                .thenReturn(List.of(new Status(statusDTOOpen.getId(), statusDTOOpen.getName()), new Status(statusDTOProgress.getId(), statusDTOProgress.getName())));
        when(orderServiceRepository.save(Mockito.any()))
                .thenAnswer(item -> item.getArguments()[0]);
        OrderServiceDTO updatedOrderServiceDTO = orderServiceService.update(updateOrderService);

        assertThat(updatedOrderServiceDTO.getRepairRequests(),
                new ADDRepairRequestInOrderServiceMatcher<>(updateOrderService.getRepairRequests(), statusDTOProgress));
    }

    @Test
    public void removeRepairRequestInOrderService_test()
    {
        repairRequestDTO1.setStatus(statusDTOProgress);
        repairRequestDTO2.setStatus(statusDTOProgress);
        OrderServiceDTO savedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1, repairRequestDTO2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        savedOrderServiceDTO.setId(100);
        OrderServiceDTO updateOrderService = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO1),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        updateOrderService.setId(100);
        when(orderServiceRepository.findById(savedOrderServiceDTO.getId()))
                .thenReturn(Optional.of(new OrderServiceDTO().toEntity(savedOrderServiceDTO)));
        when(statusRepository.findAllToView(List.of("Aberto", "Em andamento")))
                .thenReturn(List.of(new Status(statusDTOOpen.getId(), statusDTOOpen.getName()), new Status(statusDTOProgress.getId(), statusDTOProgress.getName())));
        when(orderServiceRepository.save(Mockito.any()))
                .thenAnswer(item -> item.getArguments()[0]);
        OrderServiceDTO updatedOrderServiceDTO = orderServiceService.update(updateOrderService);

        assertThat(updatedOrderServiceDTO.getRepairRequests(),
                new RemoveRepairRequestInOrderServiceMatcher<>(updateOrderService.getRepairRequests(), statusDTOProgress));
    }
}