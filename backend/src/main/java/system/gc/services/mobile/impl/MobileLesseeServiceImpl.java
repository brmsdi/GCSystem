package system.gc.services.mobile.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Lessee;
import system.gc.repositories.LesseeRepository;
import system.gc.services.mobile.MobileLesseeService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MobileLesseeServiceImpl implements MobileLesseeService {

    private final LesseeRepository lesseeRepository;

    public MobileLesseeServiceImpl(LesseeRepository lesseeRepository) {
        this.lesseeRepository = lesseeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public LesseeDTO myAccount(String username) throws EntityNotFoundException {
        Optional<Lessee> lessee = lesseeRepository.findByCPF(username);
        lessee.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado" + username));
        return new LesseeDTO(lessee.get());
    }
}