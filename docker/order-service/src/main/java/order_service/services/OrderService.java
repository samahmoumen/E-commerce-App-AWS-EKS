package order_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import order_service.dto.CreateOrderRequest;
import order_service.dto.OrderItemRequest;
import order_service.dto.Product;
import order_service.models.Order;
import order_service.models.OrderItem;
import order_service.repository.OrderItemRepository;
import order_service.repository.OrderRepository;


@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private final WebClient userServiceClient;
    private final WebClient productServiceClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, 
                        OrderItemRepository orderItemRepository, 
                        @Qualifier("userServiceWebClient") WebClient userServiceClient,
                        @Qualifier("productServiceWebClient") WebClient productServiceClient){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userServiceClient = userServiceClient;
        this.productServiceClient = productServiceClient;
    }

    public Order createOrder(CreateOrderRequest request) {
        List<OrderItem> orderItems = generateTemOrderIrItems(request);
        Order order = saveOrder(request, orderItems);
        saveOrderItems(order, orderItems);
        updateUser(order);
        updateProduct(orderItems);
        return order;
    }

    public Order getOrderById(long order_id) {
        return orderRepository.findById(order_id)
            .orElseThrow(()->new RuntimeException("Order not found with id: " + order_id));
    }

    public List<OrderItem> getOrderItemsById(long order_id) {
        return orderItemRepository.findByOrderId(order_id)
        .orElseThrow(()->new RuntimeException("Order not found with id: " + order_id));
    }

    private List<OrderItem> generateTemOrderIrItems(CreateOrderRequest request){
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemRequest> items = request.getItems();
        long tem_order_id = 0;

        for(OrderItemRequest item: items){
            Product product = productServiceClient.get()
                .uri("/{product_id}", item.getProductId())
                .retrieve()
                .bodyToMono(Product.class)
                .block();

            if(product!=null && product.getStock_left()>= item.getQuantity()){
                orderItems.add(new OrderItem(tem_order_id,
                                             product.getId(),
                                             item.getQuantity(),
                                             product.getPrice(),
                                             BigDecimal.valueOf(item.getQuantity()).multiply(product.getPrice())
                                             ));
            }else{
                throw new RuntimeException("Product: "+item.getProductId()+" don't have enough stock: "+product.getStock_left()+"<"+item.getQuantity());
            }
        }
        return orderItems;
    }

    private Order saveOrder(CreateOrderRequest request, List<OrderItem> orderItems){
        BigDecimal total = BigDecimal.ZERO;

        for(OrderItem item:orderItems){
            total=total.add(item.getItem_total());
        }
        Order order = new Order(request.getUserId(), total);
        return orderRepository.save(order);
    }

    private void saveOrderItems(Order order, List<OrderItem> orderItems){
        for(OrderItem item:orderItems){
            item.setOrder_id(order.getId());
            orderItemRepository.save(item);
        }
    }

    private void updateUser(Order order){
        userServiceClient.put()
            .uri("/{user_id}", order.getUser_id())
            .bodyValue(order.getId())
            .retrieve()
            .bodyToMono(void.class)
            .block();
    }

    private void updateProduct(List<OrderItem> items){
        for(OrderItem item:items){
            productServiceClient.put()
                .uri("/{product_id}", item.getProduct_id())
                .bodyValue(-item.getProduct_num())
                .retrieve()
                .bodyToMono(void.class)
                .block();
        }
    }
}


