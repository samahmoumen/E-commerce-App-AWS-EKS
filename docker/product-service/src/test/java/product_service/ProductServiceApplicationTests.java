package product_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import product_service.model.Product;
import product_service.repository.ProductRepository;
import product_service.services.ProductService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceApplicationTests {
	@Mock private ProductRepository productRepository;
	@InjectMocks private ProductService productService;

	@Test
	void testProductAdd() {
		Product product = new Product();
		product.setName("Shirt");
		product.setStock_left(30);
		product.setPrice(new BigDecimal(50));

		when(productRepository.save(any(Product.class))).thenReturn(product);

		Product savedProduct = productService.savaProduct(product);

		assertNotNull(savedProduct);
        assertEquals("Shirt", savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
	}

	@Test
	void testGetProductById_Success() {
        // Given
        Product product = new Product();
		product.setId(1L);
		product.setName("Shirt");
		product.setStock_left(30);
		product.setPrice(new BigDecimal(50));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        // Then
        assertNotNull(foundProduct);
        assertEquals("Shirt", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

	@Test
    void testUpdateProductWithStock_Success() {
        // Given
        Product product = new Product();
		product.setId(1L);
		product.setName("Shirt");
		product.setStock_left(30);
		product.setPrice(new BigDecimal(50));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Product updatedProduct = productService.updateProductWithStock(1L, -10);

        // Then
        assertNotNull(updatedProduct);
        assertEquals(20, updatedProduct.getStock_left());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
