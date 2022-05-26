package system.gc.services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.gc.ApplicationSetup;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationSetup applicationSetup;

    public void insertAll()
    {
        applicationSetup.execute();
    }
}
