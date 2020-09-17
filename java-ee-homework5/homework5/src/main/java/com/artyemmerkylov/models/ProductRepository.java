package com.artyemmerkylov.models;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class ProductRepository implements Serializable {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public ProductRepository() {
    }

    public List<Product> getProductsByIds(List<Integer> productsIds) {
        List<Product> products = new ArrayList<>();

        for (Integer productId : productsIds)
            products.add(em.find(Product.class, productId));

        return products;
    }

    public Product getProductById(Integer id) {
        return em.find(Product.class, id);
    }

    public List<Product> getAllProducts() {
        return em.createQuery("FROM Product", Product.class)
                .getResultList();
    }

    public BigDecimal getProductPriceById(Integer id) {
        return null;
    }

    @Transactional
    public void insertProduct(Product product) {
        em.persist(product);
    }

    @Transactional
    public void updateProduct(Product product) {
        em.merge(product);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }
}
