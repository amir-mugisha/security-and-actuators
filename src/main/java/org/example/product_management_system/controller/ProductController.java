package org.example.product_management_system.controller;


import org.example.product_management_system.dtos.ProductDTO;
import org.example.product_management_system.model.Category;
import org.example.product_management_system.model.Product;
import org.example.product_management_system.service.CategoryService;
import org.example.product_management_system.service.ProductService;
import org.example.product_management_system.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody ProductDTO productDTO) {
        Category category = categoryService.findCategoryById(productDTO.getCategoryId());
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(category);

        Product newProduct = productService.addProduct(product);
        return ResponseHandler.response("Product added successfully", HttpStatus.CREATED, newProduct);
    }

    @GetMapping
    public ResponseEntity<Object> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseHandler.response("Products retrieved successfully", HttpStatus.OK, products);
    }

    @GetMapping(path = "{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") Long productId) {
        try {
            Product product = productService.getProduct(productId);
            return ResponseHandler.response("Product retrieved successfully", HttpStatus.OK, product);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseHandler.response("Product deleted successfully", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateProduct(@PathVariable("productId") Long productId, @RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(required = false) Double price,@RequestParam(required = false) int quantity) {
        try {
            Product updatedProduct = productService.updateProduct(productId, name, description, price, quantity);
            return ResponseHandler.response("Product updated successfully", HttpStatus.OK, updatedProduct);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
