package system.gc.services.testConfigurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import system.gc.services.ServiceImpl.StatusService;

@TestConfiguration
public class StatusServiceTestConfiguration {

    @Bean
    public StatusService statusService()
    {
        return new StatusService();
    }
}
