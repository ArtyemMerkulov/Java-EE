package com.artyemmerkylov.service;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;
import com.artyemmerkylov.models.Product;
import com.artyemmerkylov.models.ProductRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ProductServiceImpl implements ProductService {

    @EJB
    private ProductRepository productRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(ProductRepr productRepr) {
        Category category = categoryRepository.findById(productRepr.getCategoryId())
                .orElse(null);
        Product product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getDescription(),
                productRepr.getPrice(),
                category);
        productRepository.insert(product);
    }

    @TransactionAttribute
    @Override
    public void update(ProductRepr productRepr) {
        Category category = categoryRepository.findById(productRepr.getCategoryId())
                .orElse(null);
        Product product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getDescription(),
                productRepr.getPrice(),
                category);

        productRepository.update(product);
    }

    @TransactionAttribute
    @Override
    public void delete(Integer id) {
        productRepository.delete(id);
    }

    @Override
    public Optional<ProductRepr> findById(Integer id) {
        return productRepository.findById(id)
                .map(ProductRepr::new);
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }
}
