package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.entities.Lessee;
import system.gc.repositories.LesseeRepository;
import system.gc.services.AuthenticationByCPFGeneric;
import system.gc.services.ChangePasswordInterface;

@Service
public class LesseeAuthenticationService implements AuthenticationByCPFGeneric<Lessee, LesseeRepository>, ChangePasswordInterface<Lessee, LesseeRepository> {}
