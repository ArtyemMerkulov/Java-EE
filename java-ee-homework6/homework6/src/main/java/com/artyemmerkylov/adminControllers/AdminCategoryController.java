package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.service.CategoryRepr;
import com.artyemmerkylov.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class AdminCategoryController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(AdminCategoryController.class);

    @EJB
    private CategoryService categoryService;

    private CategoryRepr category;

    public CategoryRepr getCategory() {
        return category;
    }

    public void setCategory(CategoryRepr category) {
        this.category = category;
    }

    public List<CategoryRepr> getAllCategories() {
        return categoryService.findAll();
    }

    public String editCategory(CategoryRepr category) {
        logger.info("User changes category " + category.getName());

        this.category = category;
        return "/admin/category.xhtml?faces-redirect=true";
    }

    public void deleteCategory(CategoryRepr category) {
        logger.info("User delete category " + category.getName());

        categoryService.delete(category.getId());
    }

    public String createCategory() {
        logger.info("User create category " + category.getName());

        this.category = new CategoryRepr();
        return "/admin/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() {
        if (category.getId() != null) {
            categoryService.update(category);
        } else {
            categoryService.insert(category);
        }
        return "/admin/categories.xhtml?faces-redirect=true";
    }
}
