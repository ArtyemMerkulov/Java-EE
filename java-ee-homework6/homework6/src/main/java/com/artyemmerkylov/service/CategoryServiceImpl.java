package com.artyemmerkylov.service;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService, Serializable {

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(),
                categoryRepr.getName(),
                categoryRepr.getDescription());
        categoryRepository.insert(category);
    }

    @TransactionAttribute
    @Override
    public void update(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(),
                categoryRepr.getName(),
                categoryRepr.getDescription());
        categoryRepository.update(category);
    }

    @TransactionAttribute
    @Override
    public void delete(Integer id) {
        categoryRepository.delete(id);
    }

    @Override
    public Optional<CategoryRepr> findById(Integer id) {
        return categoryRepository.findById(id)
                .map(CategoryRepr::new);
    }

    @Override
    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryRepr::new)
                .collect(Collectors.toList());
    }
}
