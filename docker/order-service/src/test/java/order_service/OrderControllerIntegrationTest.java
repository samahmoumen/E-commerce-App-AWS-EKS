package order_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import order_service.dto.CreateOrderRequest;
import order_service.dto.OrderItemRequest;
import order_service.dto.Product;
import order_service.dto.User;
import order_service.models.Order;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerIntegrationTest {
    
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    private static final long USER_ID = 1L;
    private static final long PRODUCT_ID = 1L;
    private static final long ORDER_ID = 1L;

    // @Test
    // void testGetUser() {
    //     ResponseEntity<String> response = testRestTemplate.getForEntity("http://order-service:8082/order", String.class);

    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertNotNull(response.getBody());
    //     assertEquals("Order service test ok!", response.getBody());
    // }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testCreateUser() {
        User user=new User();
        user.setName("Liam");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<User> response = testRestTemplate.exchange(
            "http://user-service:8080/user",
            HttpMethod.POST,
            entity,
            User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_ID, response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testCreateProduct() {
        Product product = new Product();
        // product.setId(PRODUCT_ID);
        product.setPrice(new BigDecimal(50));
        product.setStock_left(40);
        product.setName("shirt1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);

        ResponseEntity<Product> response = testRestTemplate.exchange(
            "http://product-service:8081/product",
            HttpMethod.POST,
            entity,
            Product.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(PRODUCT_ID, response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testCreateOrder(){
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(USER_ID);
        request.setItems(List.of(new OrderItemRequest(PRODUCT_ID, 2)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateOrderRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Order> response = testRestTemplate.exchange(
            "http://order-service:8082/order", 
            HttpMethod.POST, 
            entity, 
            Order.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("100.00"), response.getBody().getOrder_total());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testGetUser() {
        ResponseEntity<User> response = testRestTemplate.getForEntity(
            "http://user-service:8080/user/" + USER_ID,
            User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_ID, response.getBody().getId());
        assertEquals(1, response.getBody().getOrders().size());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testGetProduct() {
        ResponseEntity<Product> response = testRestTemplate.getForEntity(
            "http://product-service:8081/product/" + PRODUCT_ID, 
            Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(PRODUCT_ID, response.getBody().getId());
        assertEquals(38, response.getBody().getStock_left());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void testGetOrder() {
        // Given - Assume order with ID 1 exists

        // When - Send GET request
        ResponseEntity<Order> response = testRestTemplate.getForEntity("http://order-service:8082/order/" + ORDER_ID, Order.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ORDER_ID, response.getBody().getId());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void testGetOrderItems() {

        // When - Send GET request
        ResponseEntity<List> response = testRestTemplate.getForEntity("http://order-service:8082/order/items/" + ORDER_ID, List.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> firstItem = (Map<String, Object>) response.getBody().get(0);

        // Validate values
        assertEquals(ORDER_ID, ((Number) firstItem.get("order_id")).longValue());

        // Use the following code when working with the Maker class; 
        // however, this code is a simple demo and does not include the Maker classes themselves.
        // OrderItem firstOrderItem = objectMapper.convertValue(response.getBody().get(0), OrderItem.class);
        // assertEquals(orderId, firstOrderItem.getOrder_id());

    }    
}
