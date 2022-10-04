package system.gc.services.testConfigurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import system.gc.services.ServiceImpl.OrderServiceService;

@TestConfiguration
public class OrderServiceServiceTestConfiguration {

    @Bean
    public OrderServiceService orderServiceService()
    {
        return new OrderServiceService();
    }
}
