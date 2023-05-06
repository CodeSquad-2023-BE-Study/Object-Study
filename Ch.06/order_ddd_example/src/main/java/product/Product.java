package product;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private UUID id;
    private BigDecimal price;
    private String productName;

    public Product(UUID id, BigDecimal price, String productName) {
        this.id = id;
        this.price = price;
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }
}
