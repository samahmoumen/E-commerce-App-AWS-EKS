package order_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import order_service.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    Optional<List<OrderItem>> findByOrderId(long order_id);
}
