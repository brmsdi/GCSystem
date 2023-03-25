package system.gc.services.ServiceImpl;

import org.springframework.stereotype.Service;
import system.gc.entities.Lessee;
import system.gc.repositories.LesseeRepository;
import system.gc.services.AuthenticationByCPFGeneric;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
public class LesseeAuthenticationService implements AuthenticationByCPFGeneric<Lessee, LesseeRepository> {}
