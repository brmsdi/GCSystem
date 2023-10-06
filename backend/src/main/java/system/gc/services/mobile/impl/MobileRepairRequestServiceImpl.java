package system.gc.services.mobile.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.*;
import system.gc.entities.*;
import system.gc.exceptionsAdvice.exceptions.AccessDeniedOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalChangeOrderServiceException;
import system.gc.exceptionsAdvice.exceptions.IllegalSelectedRepairRequestsException;
import system.gc.repositories.RepairRequestRepository;
import system.gc.services.mobile.MobileCondominiumService;
import system.gc.services.mobile.MobileItemService;
import system.gc.services.mobile.MobileRepairRequestService;
import system.gc.services.mobile.MobileTypeProblemService;
import system.gc.services.web.impl.WebStatusService;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static system.gc.utils.TextUtils.STATUS_CONCLUDED;
import static system.gc.utils.TextUtils.STATUS_OPEN;

/**
 * @author Wisley Bruno Marques França
 * @version 1.3
 * @since 0.0.1
 */

@Service
@Log4j2
public class MobileRepairRequestServiceImpl implements MobileRepairRequestService {
    private final RepairRequestRepository repairRequestRepository;
    private final MobileItemService mobileItemService;
    private final MessageSource messageSource;
    private final MobileCondominiumService mobileCondominiumService;
    private final MobileTypeProblemService mobileTypeProblemService;

    private final WebStatusService webStatusService;

    @Autowired
    public MobileRepairRequestServiceImpl(RepairRequestRepository repairRequestRepository,
                                          MobileItemService mobileItemService,
                                          MobileCondominiumService mobileCondominiumService,
                                          MobileTypeProblemService mobileTypeProblemService,
                                          MessageSource messageSource, WebStatusService webStatusService) {
        this.repairRequestRepository = repairRequestRepository;
        this.mobileItemService = mobileItemService;
        this.mobileCondominiumService = mobileCondominiumService;
        this.mobileTypeProblemService = mobileTypeProblemService;
        this.messageSource = messageSource;
        this.webStatusService = webStatusService;
    }

    @Override
    @Transactional(readOnly = true)
    public RepairRequest findRepairRequestToAddOrRemoveItem(Integer id) {
        Optional<RepairRequest> optionalRepairRequest = repairRequestRepository.findRepairRequestToAddOrRemoveItem(id);
        return optionalRepairRequest.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_NOT_FOUND", null, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional
    public ItemDTO addItem(Integer idEmployee, Integer idRepairRequest, ItemDTO itemDTO) throws AccessDeniedOrderServiceException, IllegalChangeOrderServiceException {
        log.info("Adicionando item ao reparo");
        RepairRequest repairRequest = findRepairRequestToAddOrRemoveItem(idRepairRequest);
        statusIsEditable(STATUS_CONCLUDED, repairRequest.getOrderService().getStatus(), messageSource);
        isResponsible(idEmployee, repairRequest.getOrderService().getEmployees().stream().map(Employee::getId).collect(Collectors.toSet()), messageSource);
        return new ItemDTO(mobileItemService.save(ItemDTO.toEntityWithRepairRequest(itemDTO, repairRequest)));
    }

    @Override
    @Transactional
    public void removeItem(Integer idEmployee, Integer idRepairRequest, Integer idItem) throws AccessDeniedOrderServiceException, EntityNotFoundException, IllegalChangeOrderServiceException {
        log.info("Removendo item do reparo");
        RepairRequest repairRequest = findRepairRequestToAddOrRemoveItem(idRepairRequest);
        statusIsEditable(STATUS_CONCLUDED, repairRequest.getOrderService().getStatus(), messageSource);
        isResponsible(idEmployee, repairRequest.getOrderService().getEmployees().stream().map(Employee::getId).collect(Collectors.toSet()), messageSource);
        Optional<Item> optionalItem = getItemInItems(idItem, repairRequest.getItems());
        Item item = optionalItem.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        mobileItemService.delete(item);
    }

    @Override
    public Page<RepairRequestDTO> lesseeRepairRequests(Pageable pageable, Integer id) {
        Page<RepairRequest> repairRequestPage = repairRequestRepository.findRepairRequestForLessee(pageable, id);
        if (repairRequestPage.isEmpty()) {
            return Page.empty();
        }
        repairRequestRepository.loadLazyRepairRequestsForViewListMobile(repairRequestPage.toList());
        return repairRequestPage.map(RepairRequestDTO::forViewListMobile);
    }

    @Override
    public Page<RepairRequestDTO> searchById(Pageable pageable, Integer idLessee, Integer keySearch) {
        Page<RepairRequest> repairRequestPage = repairRequestRepository.searchRepairRequestFromLessee(pageable, idLessee, keySearch);
        if (repairRequestPage.isEmpty()) {
            return Page.empty();
        }
        repairRequestRepository.loadLazyRepairRequestsForViewListMobile(repairRequestPage.toList());
        return repairRequestPage.map(RepairRequestDTO::forViewListMobile);
    }

    @Override
    @Transactional
    public RepairRequestDTO save(Integer lesseeID, MobileRepairRequestToSaveDTO mobileRepairRequestToSaveDTO) {
        RepairRequest newRepairRequest = new RepairRequest();
        newRepairRequest.setLessee(new Lessee());
        newRepairRequest.getLessee().setId(lesseeID);
        if (!mobileCondominiumService.exists(mobileRepairRequestToSaveDTO.getCondominiumID())) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("TEXT_ERROR_CONDOMINIUM_NOT_FOUND", new Object[]{mobileRepairRequestToSaveDTO.getTypeProblemID()}, LocaleContextHolder.getLocale())
            );
        }
        newRepairRequest.setCondominium(new Condominium());
        newRepairRequest.getCondominium().setId(mobileRepairRequestToSaveDTO.getCondominiumID());
        newRepairRequest.setProblemDescription(mobileRepairRequestToSaveDTO.getProblemDescription());
        newRepairRequest.setApartmentNumber(mobileRepairRequestToSaveDTO.getApartmentNumber());
        newRepairRequest.setDate(new Date());
        Status openedStatus = webStatusService.findByName(STATUS_OPEN);
        newRepairRequest.setStatus(openedStatus);
        if (!mobileTypeProblemService.exists(mobileRepairRequestToSaveDTO.getTypeProblemID())) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("TEXT_ERROR_TYPE_PROBLEM_NOT_FOUND", new Object[]{mobileRepairRequestToSaveDTO.getTypeProblemID()}, LocaleContextHolder.getLocale())
            );
        }
        newRepairRequest.setTypeProblem(new TypeProblem());
        newRepairRequest.getTypeProblem().setId(mobileRepairRequestToSaveDTO.getTypeProblemID());
        return new RepairRequestDTO(repairRequestRepository.save(newRepairRequest));
    }

    @Override
    public RepairRequestDTO update(Integer lesseeID, RepairRequestDTO repairRequestDTO) throws ClassNotFoundException {
        Optional<RepairRequest> optionalRepairRequest = repairRequestRepository.detailsRepairRequestFromLessee(lesseeID, repairRequestDTO.getId());
        RepairRequest repairRequest = optionalRepairRequest.orElseThrow(() -> new ClassNotFoundException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        if (!Objects.equals(repairRequest.getCondominium().getId(), repairRequestDTO.getCondominium().getId())) {
            List<Condominium> condominiums = mobileCondominiumService.findAllToScreenEntity(lesseeID);
            Stream<Condominium> condominiumStream = condominiums.stream().filter(condominium -> Objects.equals(condominium.getId(), repairRequestDTO.getCondominium().getId()));
            repairRequest.setCondominium(condominiumStream.findFirst().orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_CONDOMINIUM_NOT_FOUND", new Object[]{repairRequestDTO.getCondominium().getId()}, LocaleContextHolder.getLocale()))));
        }
        if (!Objects.equals(repairRequest.getTypeProblem().getId(), repairRequestDTO.getTypeProblem().getId())) {
            List<TypeProblem> typesProblem = mobileTypeProblemService.findAllToScreenEntity();
            Stream<TypeProblem> typeProblemStream = typesProblem.stream().filter(typeProblem -> Objects.equals(typeProblem.getId(), repairRequestDTO.getTypeProblem().getId()));
            repairRequest.setTypeProblem(typeProblemStream.findFirst().orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_TYPE_PROBLEM_NOT_FOUND", new Object[]{repairRequestDTO.getTypeProblem().getId()}, LocaleContextHolder.getLocale()))));
        }
        repairRequest.setApartmentNumber(repairRequestDTO.getApartmentNumber());
        repairRequest.setProblemDescription(repairRequestDTO.getProblemDescription());
        return RepairRequestDTO.toDetailsMobile(repairRequestRepository.save(repairRequest));
    }

    @Override
    public void delete(Integer lesseeID, Integer id) throws EntityNotFoundException, IllegalSelectedRepairRequestsException {
        Optional<RepairRequest> repairRequestOptional = repairRequestRepository.searchToDelete(id);
        RepairRequest repairRequest = repairRequestOptional.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        if (!repairRequest.getStatus().getName().equalsIgnoreCase(STATUS_OPEN)) {
            throw new IllegalSelectedRepairRequestsException(messageSource.getMessage("TEXT_ERROR_DELETE_REPAIR_REQUEST_STATUS", null, LocaleContextHolder.getLocale()));
        }
        if (!Objects.equals(lesseeID, repairRequest.getLessee().getId())) {
            throw new IllegalSelectedRepairRequestsException(messageSource.getMessage("ACCESS_DENIED", null, LocaleContextHolder.getLocale()));
        }
        repairRequestRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ScreenNewRepairRequestMobileDataDTO screenData(Integer idLessee) {
        return new ScreenNewRepairRequestMobileDataDTO(
                mobileCondominiumService.findAllToScreen(idLessee),
                mobileTypeProblemService.findAllToScreen()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public RepairRequestDTO details(Integer idLessee, Integer idRepairRequest) throws ClassNotFoundException {
        Optional<RepairRequest> optionalRepairRequest = repairRequestRepository.detailsRepairRequestFromLessee(idLessee, idRepairRequest);
        RepairRequest repairRequest = optionalRepairRequest.orElseThrow(() -> new ClassNotFoundException(messageSource.getMessage("TEXT_ERROR_REPAIR_REQUEST_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        return RepairRequestDTO.toDetailsMobile(repairRequest);
    }
}