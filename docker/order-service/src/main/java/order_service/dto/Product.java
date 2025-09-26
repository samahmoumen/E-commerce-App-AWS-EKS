package order_service.dto;

import java.math.BigDecimal;

public class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int stock_left;
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
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getStock_left() {
        return stock_left;
    }
    public void setStock_left(int stock_left) {
        this.stock_left = stock_left;
    }
}
