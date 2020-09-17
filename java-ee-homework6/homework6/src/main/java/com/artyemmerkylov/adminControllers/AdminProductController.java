package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;
import com.artyemmerkylov.service.ProductRepr;
import com.artyemmerkylov.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class AdminProductController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(AdminCategoryController.class);

    @EJB
    private ProductService productService;

    @EJB
    private CategoryRepository categoryRepository;

    private ProductRepr product;

    public ProductRepr getProduct() {
        return product;
    }

    public void setProduct(ProductRepr product) {
        this.product = product;
    }

    public List<ProductRepr> getAllProducts() {
        return productService.findAll();
    }

    public String editProduct(ProductRepr product) {
        logger.info("User changes product with id " + product.getId());

        this.product = product;
        return "/admin/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductRepr product) {
        logger.info("User delete product with id " + product.getId());

        productService.delete(product.getId());
    }

    public String createProduct() {
        logger.info("User create product");

        this.product = new ProductRepr();
        return "/admin/product.xhtml?faces-redirect=true";
    }

    public String saveProduct() {
        if (product.getId() != null) {
            productService.update(product);
        } else {
            productService.insert(product);
        }
        return "/admin/products.xhtml?faces-redirect=true";
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
