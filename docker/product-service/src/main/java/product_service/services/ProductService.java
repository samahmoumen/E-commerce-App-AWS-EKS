package product_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import product_service.model.Product;
import product_service.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product savaProduct(Product product){
        return productRepository.save(product);
    }

    public Product getProductById(long id){
        return productRepository.findById(id)
            .orElseThrow(()->new RuntimeException("Product not found with id: " + id));
    }

    public Product updateProductWithStock(long id, int add_amount){
        Product product = productRepository.findById(id)
                          .orElseThrow(()->new RuntimeException("Product not found with id: " + id));
        int stock = product.getStock_left();
        if((stock + add_amount) < 0){
            throw new RuntimeException(product.getName() + ": Only " + product.getStock_left() + " left, not enough for the order!");
        }

        product.setStock_left(stock+add_amount);
        return productRepository.save(product);
    }




}
