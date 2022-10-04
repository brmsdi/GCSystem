package system.gc.services.testConfigurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import system.gc.services.ServiceImpl.RepairRequestService;

@TestConfiguration
public class RepairRequestServiceTestConfiguration {

    @Bean
    public RepairRequestService repairRequestService()
    {
        return new RepairRequestService();
    }
}
