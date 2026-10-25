package fsad.order.dto;

import jakarta.validation.constraints.Min;

public class CreateOrderRequest {

    @Min(1)
    private Long productId;

    @Min(1)
    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

