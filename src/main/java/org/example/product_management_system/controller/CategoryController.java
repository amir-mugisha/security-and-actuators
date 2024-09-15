package org.example.product_management_system.controller;

import org.example.product_management_system.model.Category;
import org.example.product_management_system.service.CategoryService;
import org.example.product_management_system.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getCategories() {
        List<Category> categories= categoryService.findAllCategories();
        return ResponseHandler.response("Categories retrieved successfully", HttpStatus.OK, categories);
    }

    @GetMapping(path = "{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable("categoryId") Long categoryId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            return ResponseHandler.response("Category retrieved successfully", HttpStatus.OK, category);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return ResponseHandler.response("Category added successfully", HttpStatus.CREATED, newCategory);
    }

    @DeleteMapping(path = "{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseHandler.response("Category deleted successfully", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestParam(required = false) String name, @RequestParam(required = false) String description) {
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, name, description);
            return ResponseHandler.response("Category updated successfully", HttpStatus.OK, updatedCategory);
        } catch (IllegalStateException e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
