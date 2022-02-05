package system.gc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Lessee;
import system.gc.repositories.LesseeRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class LesseeService {
    @Autowired
    private LesseeRepository lesseeRepository;

    @Transactional
    public LesseeDTO save(LesseeDTO newLesseeDTO) {
        log.info("Salvando novo registro de locatário no banco de dados. Nome: " + newLesseeDTO.getName());
        LesseeDTO lesseeDTO = new LesseeDTO();
        Lessee registeredLessee = lesseeRepository.save(lesseeDTO.toEntity(newLesseeDTO));
        if(registeredLessee.getId() == null) {
            log.warn("Erro ao salvar!");
            return null;
        }
        log.info("Salvo com sucesso. ID: " + registeredLessee.getId());
        return lesseeDTO.toDTO(registeredLessee);
    }

    @Transactional
    public Page<LesseeDTO> listPaginationLessees(Pageable pageable) {
        log.info("Listando locatários");
        Page<Lessee> page = lesseeRepository.findAll(pageable);
        if(!page.isEmpty()) {
            lesseeRepository.loadLazyLessees(page.toList());
        }
        return page.map(LesseeDTO::new);
    }

    @Transactional
    public void update(LesseeDTO updateLesseeDTO) throws EntityNotFoundException {
        log.info("Atualizando registro do locatário");
        Optional<Lessee> lessee = lesseeRepository.findById(updateLesseeDTO.getId());
        lessee.orElseThrow(() -> new EntityNotFoundException("Não existe registro com o id: " + updateLesseeDTO.getId()));
        LesseeDTO lesseeResultForCpf = cpfIsAvailable(updateLesseeDTO);
        if( lesseeResultForCpf != null && !Objects.equals(lessee.get().getId(), lesseeResultForCpf.getId())) {
            log.warn("Cpf não corresponde ao ID no banco de dados");
            throw new EntityNotFoundException("Cpf indisponível");
        }
        lesseeRepository.save(new LesseeDTO().toEntity(updateLesseeDTO));
        log.info("Atualizado com sucesso");
    }

    @Transactional
    public LesseeDTO cpfIsAvailable(LesseeDTO lesseeDTO) {
        log.info("Localizando registro do locatário com o cpf: " + lesseeDTO.getCpf());
        Optional<Lessee> lessee = lesseeRepository.findByCPF(lesseeDTO.getCpf());
        if(lessee.isEmpty()) {
            log.warn("O cpf: " + lesseeDTO.getCpf() + " está disponível");
            return null;
        }
        //lesseeRepository.loadLazyLessees(lessees);
        log.info("O cpf: " + lesseeDTO.getCpf() + " não está disponível");
        return new LesseeDTO().toDTO(lessee.get());
    }

    @Transactional
    public Page<LesseeDTO> findForCPF(Pageable pageable, LesseeDTO lesseeDTO) {
        log.info("Localizando registro do locatário com o cpf: " + lesseeDTO.getCpf());
        Page<Lessee> page = lesseeRepository.findByCPF(pageable, lesseeDTO.getCpf());
        if(page.isEmpty()) {
            log.warn("Registro com o cpf " + lesseeDTO.getCpf() + " não foi localizado");
            return Page.empty();
        }
        lesseeRepository.loadLazyLessees(page.toList());
        log.info("Registro do locatário com o cpf: " + lesseeDTO.getCpf() + " localizado com sucesso");
        return page.map(LesseeDTO::new);
    }

    @Transactional
    public void delete(Integer ID) throws EntityNotFoundException{
        log.info("Deletando registro com o ID: " + ID);
        Optional<Lessee> lessee = lesseeRepository.findById(ID);
        lessee.orElseThrow(() -> new EntityNotFoundException("Não existe registro com o ID: " + ID));
        lesseeRepository.delete(lessee.get());
        log.info("Registro deletado com sucesso");
    }
}
