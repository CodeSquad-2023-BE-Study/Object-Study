package order;

import product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static order.OrderValidator.validateProduct;
import static order.OrderValidator.validateState;

public class Order {
    private UUID id;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private BigDecimal price;

    public Order(UUID id, Product product) {
        this.id = id;
        this.orderItems = new ArrayList<>(List.of(new OrderItem(product)));
        this.status = OrderStatus.CREATED;
        this.price = product.getPrice();
    }

    public void complete() {
        validateState();
        this.status = OrderStatus.COMPLETED;
    }

    public void addOrder(Product product) {
        validateState();
        validateProduct(product);
        orderItems.add(new OrderItem(product));
        price = price.add(product.getPrice());
    }

    public void removeOrder(UUID id) {
        validateState();
        final OrderItem orderItem = getOrderItem(id);
        orderItems.remove(orderItem);

        price = price.subtract(orderItem.getPrice());
    }

    private OrderItem getOrderItem(UUID id) {
        return orderItems.stream().filter(e -> e.getId()==id)
                .findAny()
                .orElseThrow();
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
