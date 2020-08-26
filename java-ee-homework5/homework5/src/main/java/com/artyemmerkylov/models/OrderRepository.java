package com.artyemmerkylov.models;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;

@ApplicationScoped
@Named
public class OrderRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private PurchaseRepository purchaseRepository;

    public OrderRepository() {
    }

    public List<Order> getAllOrders() {
        return em.createQuery("FROM Order ", Order.class)
                .getResultList();
    }

    @Transactional
    public void updateOrder(Order order) throws SQLException {
//        String ordersQuery = "UPDATE orders SET buyer_name=?, phone=?, details=? WHERE id=?;";
//
//        String productOrdersQuery = "UPDATE product_orders SET total=? WHERE order_id=? AND product_id=?;";
//
//        List<Purchase> purchaseList = order.getPurchaseList();
//
//        conn.setAutoCommit(false);
//
//        try (PreparedStatement ordersStmt = conn.prepareStatement(ordersQuery)) {
//            ordersStmt.setString(1, order.getBuyerName());
//            ordersStmt.setString(2, order.getPhone());
//            ordersStmt.setString(3, order.getDetails());
//            ordersStmt.setInt(4, order.getId());
//
//            int rowAffected = ordersStmt.executeUpdate();
//
//            if (rowAffected >= 1) {
//                try (PreparedStatement productOrdersStmt = conn.prepareStatement(productOrdersQuery)) {
//                    try {
//                        for (Purchase purchase : purchaseList) {
//                            productOrdersStmt.setInt(1, purchase.getAmount());
//                            productOrdersStmt.setInt(2, order.getId());
//                            productOrdersStmt.setInt(3, purchase.getProduct().getId());
//
//                            productOrdersStmt.addBatch();
//                        }
//
//                        productOrdersStmt.executeBatch();
//
//                        conn.commit();
//                    } catch (SQLException e) {
//                        conn.rollback();
//                    }
//                } catch (SQLException e) {
//                    conn.rollback();
//                }
//            } else {
//                conn.rollback();
//            }
//        } catch (SQLException e) {
//            conn.rollback();
//        } finally {
//            conn.setAutoCommit(true);
//        }
    }

    @Transactional
    public void deleteOrder(Integer id) {
        Order order = em.find(Order.class, id);
        if (order != null) {
            em.remove(order);
        }
    }

//    public void deleteProductFromOrder(Order order, Product product) throws SQLException {
//        TypedQuery<Category> categoryQuery = em.createQuery("SELECT Category.id FROM Category WHERE Category.name=:name",
//                Category.class);
//
//
//        String query = "DELETE FROM product_orders WHERE order_id=? AND product_id=?;";
//
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, order.getId());
//            stmt.setInt(2, product.getId());
//
//            stmt.executeUpdate();
//        }
//    }
}
