package order_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${USER_SERVICE_URL:http://localhost:8080/user}")
    private String userServiceUrl;

    @Value("${PRODUCT_SERVICE_URL:http://localhost:8080/product}")
    private String productServiceUrl;

    @Bean
    @Qualifier("userServiceWebClient")
    public WebClient userServiceWebClient(WebClient.Builder builder) {
        return builder.baseUrl(userServiceUrl).build();
    }

    @Bean
    @Qualifier("productServiceWebClient")
    public WebClient productServiceWebClient(WebClient.Builder builder) {
        return builder.baseUrl(productServiceUrl).build();
    }
}