package order_service.dto;

import java.util.List;

public class CreateOrderRequest {
    private long userId;
    private List<OrderItemRequest> items;

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}