package order_service.dto;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String name;
    private List<Long> orders = new ArrayList<>();
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Long> getOrders() {
        return orders;
    }
    public void setOrders(List<Long> orders) {
        this.orders = orders;
    }

}
