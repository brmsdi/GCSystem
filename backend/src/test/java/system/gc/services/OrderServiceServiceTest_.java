package system.gc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import system.gc.configuration.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.dtos.*;
import system.gc.services.ServiceImpl.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Import(OrderServiceServiceTestConfiguration.class)
public class OrderServiceServiceTest_ {

    @Autowired
    private OrderServiceService orderServiceService;

    @Autowired
    private RepairRequestService repairRequestService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private TypeProblemService typeProblemService;

    @Autowired
    private LesseeService lesseeService;

    @Autowired
    private CondominiumService condominiumService;

    @Autowired
    private EmployeeService employeeService;

    private StatusDTO statusDTOOpen;

    private StatusDTO statusDTOConcluded;

    private StatusDTO statusDTOCanceled;

    private Date today;

    private Date yesterday;

    private TypeProblemDTO typeProblemDTOOthers;

    private LesseeDTO oneLesseeDTO;

    private CondominiumDTO oneCondominiumDTO;

    private EmployeeDTO oneEmployeeDTO;

    @BeforeEach
    public void setup()
    {
        statusDTOOpen = new StatusDTO(statusService.findByName("Aberto"));
        statusDTOConcluded = new StatusDTO(statusService.findByName("Concluído"));
        statusDTOCanceled = new StatusDTO(statusService.findByName("Cancelado"));
        typeProblemDTOOthers = new TypeProblemDTO(typeProblemService.findByName("Outros"));
        oneLesseeDTO = lesseeService.findByCPF(new LesseeDTO("12563256347"));
        oneCondominiumDTO = condominiumService.listPaginationCondominium(PageRequest.of(0, 1)).toList().get(0);
        oneEmployeeDTO = employeeService.findByCPF(new EmployeeDTO("72558678342"));
        LocalDate localDate = LocalDate.now();
        today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        yesterday = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

    }

    @Test
    public void successAddOrderService_test() {
        RepairRequestDTO repairRequestDTOSaved = repairRequestService.save(new RepairRequestDTO(
                "Descrição 1",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "10",
                statusDTOOpen
        ));

        RepairRequestDTO repairRequestDTOSaved2 = repairRequestService.save(new RepairRequestDTO(
                "Descrição 2",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "11",
                statusDTOOpen
        ));

        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTOSaved, repairRequestDTOSaved2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        OrderServiceDTO saved = orderServiceService.save(unsavedOrderServiceDTO);
        assertNotNull(saved.getId());
    }

    @Test
    public void statusRepairRequestNotOpenInNewOrderService_test()
    {
        RepairRequestDTO repairRequestDTO = repairRequestService.searchRepairRequest(PageRequest.of(0, 1), new LesseeDTO("45565625634")).toList().get(0);
        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTO),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        try {
            orderServiceService.save(unsavedOrderServiceDTO);
            fail("Deveria lançar uma IllegalSelectedRepairRequestsException");
        } catch (IllegalSelectedRepairRequestsException error)
        {
            assertThat(error.getMessage(), is("Solicitações de reparo com o status invalido. Selecione as solicitações com o status 'Aberto'"));
        }
    }

    @Test
    public void orderServiceNotFoundForUpdate_test()
    {
        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        unsavedOrderServiceDTO.setId(500);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> orderServiceService.update(unsavedOrderServiceDTO));
        assertThat(exception.getMessage(), is("Registro não encontrado"));
    }

    @Test
    public void successCloseOrderService_test()
    {
        RepairRequestDTO repairRequestDTOSaved = repairRequestService.save(new RepairRequestDTO(
                "Descrição 1",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "10",
                statusDTOOpen
        ));

        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTOSaved),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        OrderServiceDTO saved = orderServiceService.save(unsavedOrderServiceDTO);
        saved.setStatus(statusDTOConcluded);
        orderServiceService.update(saved);
        OrderServiceDTO concludedOrderService = orderServiceService.searchOrderService(saved.getId()).toList().get(0);
        assertThat(concludedOrderService.getStatus().getId(), is(statusDTOConcluded.getId()));
    }

    @Test
    public void successCancelOrderService_test()
    {
        RepairRequestDTO repairRequestDTOSaved = repairRequestService.save(new RepairRequestDTO(
                "Descrição 1",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "10",
                statusDTOOpen
        ));

        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTOSaved),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        OrderServiceDTO saved = orderServiceService.save(unsavedOrderServiceDTO);
        saved.setStatus(statusDTOCanceled);
        orderServiceService.update(saved);
        OrderServiceDTO canceledOrderService = orderServiceService.searchOrderService(saved.getId()).toList().get(0);
        assertThat(canceledOrderService.getStatus().getId(), is(statusDTOCanceled.getId()));
    }

    @Test
    public void updateStatusProgressToStatusConcludedInRepairRequest_test()
    {
        RepairRequestDTO repairRequestDTOSaved = repairRequestService.save(new RepairRequestDTO(
                "Descrição 1",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "10",
                statusDTOOpen
        ));

        RepairRequestDTO repairRequestDTOSaved2 = repairRequestService.save(new RepairRequestDTO(
                "Descrição 2",
                today,
                typeProblemDTOOthers,
                oneLesseeDTO,
                oneCondominiumDTO,
                "11",
                statusDTOOpen
        ));

        OrderServiceDTO unsavedOrderServiceDTO = new OrderServiceDTO(
                today,
                yesterday,
                Set.of(repairRequestDTOSaved, repairRequestDTOSaved2),
                Set.of(oneEmployeeDTO),
                statusDTOOpen);
        OrderServiceDTO saved = orderServiceService.save(unsavedOrderServiceDTO);
        saved.setStatus(statusDTOConcluded);
        orderServiceService.update(saved);
        OrderServiceDTO concludedOrderService = orderServiceService.searchOrderService(saved.getId()).toList().get(0);
        Long size = concludedOrderService
                .getRepairRequests()
                .parallelStream()
                .filter(repairRequestDTO -> Objects.equals(repairRequestDTO.getStatus().getId(), statusDTOConcluded.getId()))
                .count();
        Integer integerCount = Integer.valueOf(String.valueOf(size));
        assertThat(saved.getRepairRequests().size(), is(integerCount));
    }
}
