package order_service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import order_service.dto.CreateOrderRequest;
import order_service.models.Order;
import order_service.models.OrderItem;
import order_service.services.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/order")
public class Controller {

    private OrderService orderService;

    @Autowired
    public Controller(OrderService orderService){
        this.orderService = orderService;
    }
    

    @GetMapping
    public String test() {
        return "Order service test ok!";
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }

    @GetMapping("/{order_id}")
    public Order getOrder(@PathVariable long order_id) {
        return orderService.getOrderById(order_id);
    }

    @GetMapping("/items/{order_id}")
    public List<OrderItem> getOrderItems(@PathVariable long order_id) {
        return orderService.getOrderItemsById(order_id);
    }
    
    
    
    
}
