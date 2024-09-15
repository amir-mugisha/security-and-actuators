package org.example.product_management_system.service;

import jakarta.transaction.Transactional;
import org.example.product_management_system.model.Category;
import org.example.product_management_system.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, String name, String description) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Category with id " + id + " not found")
        );

        if(name != null && !name.isEmpty()){
            category.setName(name);
        }

        if(description != null && !description.isEmpty()){
            category.setDescription(description);
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Category with id " + id + " not found"));
        categoryRepository.deleteById(id);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Category with id "+ id + " not found"));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
