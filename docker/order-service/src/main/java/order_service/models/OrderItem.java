package order_service.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long orderId;

    @Column(nullable = false)
    private long product_id;

    @Column(nullable = false)
    private int product_num;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal item_total;

    public OrderItem(){}
    
    public OrderItem(long order_id, long product_id, int product_num, BigDecimal price, BigDecimal item_total) {
        this.orderId = order_id;
        this.product_id = product_id;
        this.product_num = product_num;
        this.price = price;
        this.item_total = item_total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder_id() {
        return orderId;
    }

    public void setOrder_id(long order_id) {
        this.orderId = order_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getItem_total() {
        return item_total;
    }

    public void setItem_total(BigDecimal item_total) {
        this.item_total = item_total;
    }

}
