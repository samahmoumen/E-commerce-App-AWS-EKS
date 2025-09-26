package order_service.models;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long user_id;

    @Column(nullable = false)
    private BigDecimal order_total;

    public Order(){}

    public Order(long user_id, BigDecimal order_total) {
        this.user_id = user_id;
        this.order_total = order_total;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getOrder_total() {
        return order_total;
    }

    public void setOrder_total(BigDecimal order_total) {
        this.order_total = order_total;
    }

}