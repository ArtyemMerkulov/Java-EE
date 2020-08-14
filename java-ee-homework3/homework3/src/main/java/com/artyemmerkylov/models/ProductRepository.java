package com.artyemmerkylov.models;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final Connection conn;

    public ProductRepository(Connection conn) throws SQLException {
        this.conn = conn;

        createTableIfNotExists(conn);
    }

    public static List<Product> getProductsByIds(Connection conn, List<Integer> productsIds) throws SQLException {
        String query = "SELECT     products.id, products.name, products.description, products.price, products.img, categories.name\n" +
                "       FROM       products\n" +
                "       INNER JOIN categories ON category_id=categories.id\n" +
                "       WHERE      products.id IN (inClause);";

        StringBuilder sb = new StringBuilder();

        if (productsIds != null && productsIds.size() > 0) {
            for (int i = 0; i < productsIds.size() - 1; i++) sb.append("?, ");
            sb.append("?");
        } else return null;

        query = query.replace("inClause", sb);

        List<Product> products = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 1; i <= productsIds.size(); i++) pstmt.setInt(i, productsIds.get(i - 1));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next())
                    products.add(new Product(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP),
                            rs.getString(5),
                            rs.getString(6))
                    );
            }
        }

        return products;
    }

    public Product getProductById(Integer id) throws SQLException {
        String query = "SELECT     products.id, products.name, products.description, products.price, products.img, categories.name\n" +
                "       FROM       products\n" +
                "       INNER JOIN categories ON category_id=categories.id\n" +
                "       WHERE      products.id=?;";

        Product product = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product(rs.getInt(1), rs.getString(2),
                            rs.getString(3), rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP),
                            rs.getString(5), rs.getString(6));
                }
            }
        }

        return product;
    }

    public List<Product> getAllProducts() throws SQLException {
        String query = "SELECT     products.id, products.name, products.description, products.price, products.img, categories.name, categories.name\n" +
                "       FROM       products\n" +
                "       INNER JOIN categories ON category_id=categories.id;";

        List<Product> res = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    res.add(new Product(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP),
                            rs.getString(5),
                            rs.getString(6)
                    ));
                }
            }
        }

        return res;
    }

    public BigDecimal getProductPriceById(Integer id) throws SQLException {
        String query = "SELECT products.price FROM products WHERE products.id=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return BigDecimal.valueOf(rs.getInt(1));
            }
        }

        return null;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS products (\n" +
                "  id           int             NOT NULL AUTO_INCREMENT,\n" +
                "  name         varchar(40)     NOT NULL,\n" +
                "  description  varchar(255)    NOT NULL DEFAULT (''),\n" +
                "  price        decimal(12,8)   NOT NULL DEFAULT (0),\n" +
                "  img          varchar(54)     NOT NULL DEFAULT ('resources/images/products/default.png'),\n" +
                "  category_id  int             DEFAULT NULL,\n" +
                "                               PRIMARY KEY (id),\n" +
                "                               UNIQUE KEY id (id),\n" +
                "                               UNIQUE KEY name (name),\n" +
                "                               KEY fk_categories_products (category_id),\n" +
                "                               CONSTRAINT fk_categories_products FOREIGN KEY (category_id) REFERENCES categories (id) ON UPDATE CASCADE\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }
}
