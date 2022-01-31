package system.gc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.dtos.SpecialtyDTO;
import system.gc.repositories.SpecialtyRepository;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll().stream().map(SpecialtyDTO::new).toList();
    }

}
