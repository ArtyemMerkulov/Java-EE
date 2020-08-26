package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;
import com.artyemmerkylov.models.Product;
import com.artyemmerkylov.models.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class AdminProductController implements Serializable {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    private Product product;

    private Category category;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getAllCategoriesNames() {
        return categoryRepository.getAllCategoriesNames();
    }

    public String getProductCategoryName(Product product) {
        if (product.getId() == null) return "";

        return categoryRepository.getCategoryNameById(product.getCategoryId());
    }

    public String editProduct(Product product) {
        this.product = product;
        return "/admin/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productRepository.deleteProduct(product.getId());
    }

    public String createProduct() {
        this.product = new Product();
        return "/admin/product.xhtml?faces-redirect=true";
    }

    public String saveProduct() {
        if (product.getId() != null) {
            productRepository.updateProduct(product);
        } else {
            productRepository.insertProduct(product);
        }

        return "/admin/products.xhtml?faces-redirect=true";
    }
}
