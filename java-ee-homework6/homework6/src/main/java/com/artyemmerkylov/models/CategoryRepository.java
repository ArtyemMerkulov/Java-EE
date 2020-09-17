package com.artyemmerkylov.models;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public CategoryRepository() {
    }

    public void insert(Category category) {
        em.persist(category);
    }

    public void update(Category category) {
        em.merge(category);
    }

    public void delete(Integer id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    public Optional<Category> findById(Integer id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            return Optional.of(category);
        }
        return Optional.empty();
    }

    public List<Category> findAll() {
        return em.createQuery("from Category", Category.class)
                .getResultList();
    }

    public Optional<Category> findByName(String name) {
        Category category = em.createQuery("from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
        if (category != null) {
            return Optional.of(category);
        } else {
            return Optional.empty();
        }
    }
}
