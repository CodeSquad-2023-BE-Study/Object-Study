package order.repository;

import order.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryOrderRepository implements OrderRepository{
    private Map<UUID, Order> repository = new HashMap<>();

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public void save(Order order) {
        repository.put(order.getId(), order);
    }
}
