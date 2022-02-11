package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.dtos.LesseeDTO;
import system.gc.entities.Lessee;
import system.gc.repositories.LesseeRepository;
import system.gc.services.AuthenticationByCPFGenericImpl;

@Service
public class AuthenticationLessee implements AuthenticationByCPFGenericImpl<LesseeDTO, Lessee, LesseeRepository> {

}
