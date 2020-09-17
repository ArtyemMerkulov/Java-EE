package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/admin/category.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        categoryRepository.deleteCategory(category.getId());
    }

    public String createCategory() {
        this.category = new Category();
        return "/admin/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() {
        if (category.getId() != null) {
            categoryRepository.updateCategory(category);
        } else {
            categoryRepository.insertCategory(category);
        }

        return "/admin/categories.xhtml?faces-redirect=true";
    }
}
