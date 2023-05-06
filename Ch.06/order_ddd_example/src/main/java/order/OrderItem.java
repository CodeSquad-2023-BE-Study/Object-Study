package order;

import product.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    private UUID productId;
    private BigDecimal price;

    public OrderItem(Product product) {
        this.productId = product.getId();
        this.price = product.getPrice();
    }

    public UUID getId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
