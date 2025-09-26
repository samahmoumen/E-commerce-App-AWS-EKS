package product_service.api;

import org.springframework.web.bind.annotation.RestController;

import product_service.model.Product;
import product_service.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/product")
public class Controller {

    private ProductService productService;

    @Autowired
    public Controller(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String test() {
        return "Product service is ok!";
    }

    @GetMapping("/{id}")
    public Product getMethodName(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product postMethodName(@RequestBody Product product) {        
        return productService.savaProduct(product);
    }

    @PutMapping("/{id}")
    public Product putMethodName(@PathVariable long id, @RequestBody int add_amount) {        
        return productService.updateProductWithStock(id, add_amount);
    }    
}
