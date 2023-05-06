package order.dto;

import product.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderRequest {

    UUID id;
    BigDecimal price;
    String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return new Product(this.id, this.price, name);
    }
}
