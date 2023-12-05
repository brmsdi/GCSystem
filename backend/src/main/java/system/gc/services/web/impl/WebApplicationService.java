package system.gc.services.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.ApplicationSetup;

/**
 * @author Wisley Bruno Marques França
 * @since 0.0.1
 * @version 1.3
 */
@Service
public class WebApplicationService {
    @Autowired
    private ApplicationSetup applicationSetup;

    public void insertAll()
    {
        applicationSetup.execute();
    }
}
