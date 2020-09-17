package com.artyemmerkylov.models;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Named
public class CategoryRepository implements Serializable {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public CategoryRepository() {
    }

    public List<Category> getAllCategories() {
        return em.createQuery("FROM Category", Category.class)
                .getResultList();
    }

    @Transactional
    public List<Product> getAllProductsByCategoryName(String categoryName) {
        TypedQuery<Category> categoryQuery = em.createQuery("SELECT Category.id FROM Category WHERE Category.name=:name",
                Category.class);

        Integer categoryId = categoryQuery.setParameter("name", categoryName).getSingleResult().getId();

        TypedQuery<Product> query = em.createQuery("FROM Product WHERE Product.categoryId=:categoryId",
                Product.class);

        return query.setParameter("categoryId", categoryId).getResultList();
    }

    public List<String> getAllCategoriesNames() {
        return em.createQuery("SELECT c.name FROM Category AS c", String.class)
                .getResultList();
    }

    @Transactional
    public void insertCategory(Category category) {
        em.persist(category);
    }

    @Transactional
    public void updateCategory(Category category) {
        em.merge(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    public String getCategoryNameById(Integer id) {
        TypedQuery<String> query = em.createQuery("SELECT Category.name FROM Category WHERE Category.id=:id",
                String.class);

        return query.setParameter("id", id).getSingleResult();
    }
}
