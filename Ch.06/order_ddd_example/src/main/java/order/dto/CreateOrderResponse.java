package order.dto;

import java.util.UUID;

public class CreateOrderResponse {
    UUID id;
    public CreateOrderResponse(UUID id) {
        this.id = id;
    }
}
