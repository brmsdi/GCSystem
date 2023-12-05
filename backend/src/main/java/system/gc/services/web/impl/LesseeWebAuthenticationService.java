package system.gc.services.web.impl;

import org.springframework.stereotype.Service;
import system.gc.entities.Lessee;
import system.gc.repositories.LesseeRepository;
import system.gc.services.web.WebAuthenticationByCPFGeneric;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
public class LesseeWebAuthenticationService implements WebAuthenticationByCPFGeneric<Lessee, LesseeRepository> {}
