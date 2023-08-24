package system.gc.services.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    @Autowired
    public MobileLesseeServiceImpl(LesseeRepository lesseeRepository, MessageSource messageSource) {
        this.lesseeRepository = lesseeRepository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional(readOnly = true)
    public LesseeDTO myAccount(String username) throws EntityNotFoundException {
        Optional<Lessee> lessee = lesseeRepository.findByCPF(username);
        lessee.orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("TEXT_ERROR_REGISTER_NOT_FOUND", null, LocaleContextHolder.getLocale())));
        return new LesseeDTO(lessee.get());
    }
}