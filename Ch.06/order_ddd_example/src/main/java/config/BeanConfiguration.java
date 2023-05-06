package config;

import order.DomainOrderService;
import order.repository.OrderRepository;
import order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    OrderService orderService(OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
}
