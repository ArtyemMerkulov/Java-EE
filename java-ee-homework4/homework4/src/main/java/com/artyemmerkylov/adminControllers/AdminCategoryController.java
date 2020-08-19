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
public class AdminCategoryController implements Serializable {

    @Inject
    private CategoryRepository categoryRepository;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryRepository.getAllCategories();
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/admin/category.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        try {
            categoryRepository.deleteCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String createCategory() {
        this.category = new Category();
        return "/admin/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() {
        try {
            if (category.getId() != null) {
                categoryRepository.updateCategory(category);
            } else {
                categoryRepository.insertCategory(category);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return "/admin/categories.xhtml?faces-redirect=true";
    }
}
