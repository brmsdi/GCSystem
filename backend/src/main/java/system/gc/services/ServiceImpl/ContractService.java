package system.gc.services.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.dtos.ContractDTO;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Contract;
import system.gc.repositories.ContractRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Wisley Bruno Marques França
 */
@Service
@Slf4j
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private LesseeService lesseeService;

    @Transactional
    public ContractDTO save(ContractDTO contractDTO) {
        log.info("Salvando novo registro de contrato no banco de dados");
        ContractDTO contractDTOService = new ContractDTO();
        Contract registeredContract = contractRepository.save(contractDTOService.toEntity(contractDTO));
        log.info("Salvo com sucesso. ID: " + registeredContract.getId());
        return contractDTOService.toDTO(registeredContract);
    }

    @Transactional
    public Page<ContractDTO> listPaginationContract(Pageable pageable) {
        log.info("Buscando contratos");
        Page<Contract> pageContract = contractRepository.findAll(pageable);
        contractRepository.loadLazyContracts(pageContract.toList());
        return pageContract.map(ContractDTO::new);
    }

    @Transactional
    public void update(ContractDTO contractDTO) throws EntityNotFoundException {
        Optional<Contract> contract = contractRepository.findById(contractDTO.getId());
        contract.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        contractRepository.save(new ContractDTO().toEntity(contractDTO));
    }

    @Transactional
    public Page<ContractDTO> searchContract(Pageable pageable, LesseeDTO lesseeDTO) {
        log.info("Buscando registro de contrato");
        LesseeDTO lessee = lesseeService.findByCPF(lesseeDTO);
        if (lessee == null) {
            log.warn("Locatário com o CPF: " + lesseeDTO.getCpf() + " não foi localizado");
            return Page.empty();
        }
        Page<Contract> pageContract = contractRepository.findContractsForLessee(pageable, lessee.getId());
        contractRepository.loadLazyContracts(pageContract.toList());
        return pageContract.map(ContractDTO::new);
    }

    @Transactional
    public ContractDTO findByID(Integer ID) {
        log.info("Buscando registro de contrato com o id: " + ID);
        Optional<Contract> contract = contractRepository.findById(ID);
        contract.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        contractRepository.loadLazyContracts(List.of(contract.get()));
        return new ContractDTO(contract.get());
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException {
        log.info("Deletando registro com o ID: " + ID);
        Optional<Contract> contract = contractRepository.findById(ID);
        contract.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
        contractRepository.delete(contract.get());
        log.info("Registro deletado com sucesso");
    }

    @Transactional
    public void deleteAll()
    {
        log.info("Deletando todos");
        contractRepository.deleteAll();
    }

}
