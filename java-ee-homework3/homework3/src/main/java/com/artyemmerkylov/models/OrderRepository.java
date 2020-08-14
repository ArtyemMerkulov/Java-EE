package com.artyemmerkylov.models;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderRepository {

    private final Connection conn;

    public OrderRepository(Connection conn) throws SQLException {
        this.conn = conn;

        createTablesIfNotExists(conn);
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

    private void createTablesIfNotExists(Connection conn) throws SQLException {
        String createOrdersQuery = "CREATE TABLE IF NOT EXISTS orders (\n" +
                "   id          int             AUTO_INCREMENT\n" +
                "                               UNIQUE\n" +
                "                               NOT NULL,\n" +
                "   buyer_name  VARCHAR (100)   NOT NULL,\n" +
                "   phone       VARCHAR (20)    NOT NULL,\n" +
                "   details     VARCHAR (255)   NOT NULL,\n" +
                "                               PRIMARY KEY (id)\n" +
                ");";

        String createProductsOrdersQuery = "CREATE TABLE IF NOT EXISTS product_orders (\n" +
                "  order_id     int     NOT NULL,\n" +
                "  product_id   int     NOT NULL,\n" +
                "  total        int     NOT NULL,\n" +
                "                       KEY fk_product (product_id),\n" +
                "                       KEY fk_order (order_id),\n" +
                "                       CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id) ON UPDATE CASCADE,\n" +
                "                       CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id) ON UPDATE CASCADE\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createOrdersQuery);
            stmt.execute(createProductsOrdersQuery);
        }
    }

    public List<Product> getProductsInCart(Cart cart) throws SQLException {
        return ProductRepository.getProductsByIds(conn, cart.getProductsIds());
    }
}
