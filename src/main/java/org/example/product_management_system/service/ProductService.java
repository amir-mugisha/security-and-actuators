package org.example.product_management_system.service;

import org.example.product_management_system.model.Product;
import org.example.product_management_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, String name, String description, Double price, int quantity) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Product with id " + id + " not found")
        );

        if(name != null && !name.isEmpty()){
            product.setName(name);
        }

        if(description != null && !description.isEmpty()){
            product.setDescription(description);
        }

        if(price != null){
            product.setPrice(price);
        }

        if(quantity != 0){
            product.setQuantity(quantity);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Product with id " + id + " not found"));

        productRepository.deleteById(id);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(()
                ->new IllegalStateException("Product with id " + id + " not found"));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
