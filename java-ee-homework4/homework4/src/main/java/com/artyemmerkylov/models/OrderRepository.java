package com.artyemmerkylov.models;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Named
public class OrderRepository {

    @Inject
    private ServletContext context;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private PurchaseRepository purchaseRepository;

    private Connection conn;

    public OrderRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("connection");
        createTableIfNotExists(conn);
    }

    public void createOrder(Cart cart) throws SQLException {
        String ordersQuery = "INSERT INTO orders (buyer_name, phone, details) VALUES (?, ?, ?)";

        String productOrdersQuery = "INSERT INTO product_orders (order_id, product_id, total) VALUES (?, ?, ?)";

        conn.setAutoCommit(false);

        try (PreparedStatement ordersPstmt = conn.prepareStatement(ordersQuery, Statement.RETURN_GENERATED_KEYS)) {
            ordersPstmt.setString(1, "Test buyer");
            ordersPstmt.setString(2, "+7-800-555-35-35");
            ordersPstmt.setString(3, "111222, г. Москва, пр. Ленина, д. 1, кв. 1");

            int rowAffected = ordersPstmt.executeUpdate();

            try (ResultSet rs = ordersPstmt.getGeneratedKeys()) {
                int orderId = 0;
                if(rs.next()) orderId = rs.getInt(1);

                if (rowAffected == 1) {
                    try (PreparedStatement productOrdersPstmt = conn.prepareStatement(productOrdersQuery)) {
                        int finalOrderId = orderId;
                        Map<Integer, Integer> productsParams = cart.getProductsParams();

                        productsParams.forEach((productId, totalProducts) -> {
                            try {
                                productOrdersPstmt.setInt(1, finalOrderId);
                                productOrdersPstmt.setInt(2, productId);
                                productOrdersPstmt.setInt(3, totalProducts);

                                productOrdersPstmt.addBatch();
                            } catch (SQLException e) {
                                try {
                                    conn.rollback();
                                } catch (SQLException ex) {
                                    ex.getStackTrace();
                                }
                            }
                        });

                        productOrdersPstmt.executeBatch();

                        conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                } else {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS orders (\n" +
                "   id          int             AUTO_INCREMENT\n" +
                "                               UNIQUE\n" +
                "                               NOT NULL,\n" +
                "   buyer_name  VARCHAR (100)   NOT NULL,\n" +
                "   phone       VARCHAR (20)    NOT NULL,\n" +
                "   details     VARCHAR (255)   NOT NULL,\n" +
                "                               PRIMARY KEY (id)\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }

    public List<Product> getProductsInCart(Cart cart) throws SQLException {
        return productRepository.getProductsByIds(cart.getProductsIds());
    }

    public List<Order> getAllOrders() throws SQLException {
        String query = "SELECT * FROM orders ORDER BY id ASC;";

        List<Order> orders = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int orderId = rs.getInt(1);

                    orders.add(new Order(
                            orderId,
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            purchaseRepository.getPurchaseListByOrderId(orderId)
                    ));
                }
            }
        }

        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String ordersQuery = "UPDATE orders SET buyer_name=?, phone=?, details=? WHERE id=?;";

        String productOrdersQuery = "UPDATE product_orders SET total=? WHERE order_id=? AND product_id=?;";

        List<Purchase> purchaseList = order.getPurchaseList();

        conn.setAutoCommit(false);

        try (PreparedStatement ordersStmt = conn.prepareStatement(ordersQuery)) {
            ordersStmt.setString(1, order.getBuyerName());
            ordersStmt.setString(2, order.getPhone());
            ordersStmt.setString(3, order.getDetails());
            ordersStmt.setInt(4, order.getId());

            int rowAffected = ordersStmt.executeUpdate();

            if (rowAffected >= 1) {
                try (PreparedStatement productOrdersStmt = conn.prepareStatement(productOrdersQuery)) {
                    try {
                        for (Purchase purchase : purchaseList) {
                            productOrdersStmt.setInt(1, purchase.getAmount());
                            productOrdersStmt.setInt(2, order.getId());
                            productOrdersStmt.setInt(3, purchase.getProduct().getId());

                            productOrdersStmt.addBatch();
                        }

                        productOrdersStmt.executeBatch();

                        conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                } catch (SQLException e) {
                    conn.rollback();
                }
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void deleteOrder(Order order) throws SQLException {
        String query = "DELETE FROM orders WHERE id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, order.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteProductFromOrder(Order order, Product product) throws SQLException {
        String query = "DELETE FROM product_orders WHERE order_id=? AND product_id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, order.getId());
            stmt.setInt(2, product.getId());

            stmt.executeUpdate();
        }
    }
}
