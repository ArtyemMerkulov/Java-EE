package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;
import com.artyemmerkylov.models.Product;
import com.artyemmerkylov.models.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
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
        List<Product> products = null;

        try {
            products = productRepository.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getAllCategoriesNames() {
        List<String> categoriesNames = null;

        try {
            categoriesNames = categoryRepository.getAllCategoriesNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoriesNames;
    }

    public String editProduct(Product product) {
        this.product = product;
        return "/admin/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        try {
            productRepository.deleteProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String createProduct() {
        this.product = new Product();
        return "/admin/product.xhtml?faces-redirect=true";
    }

    public String saveProduct() {
        try {
            if (product.getId() != null) {
                productRepository.updateProduct(product);
            } else {
                productRepository.insertProduct(product);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return "/admin/products.xhtml?faces-redirect=true";
    }
}
