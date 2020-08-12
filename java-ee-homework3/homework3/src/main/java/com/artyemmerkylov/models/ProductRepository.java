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

//    public void insert(Product product) throws SQLException {
//        try (PreparedStatement stmt = conn.prepareStatement(
//                "insert into products(name, description, price) values (?, ?, ?);")) {
//            stmt.setString(1, product.getName());
//            stmt.setString(2, product.getDescription());
//            stmt.setBigDecimal(3, product.getPrice());
//            stmt.execute();
//        }
//    }
//
//    public void update(Product product) throws SQLException {
//        try (PreparedStatement stmt = conn.prepareStatement(
//                "update products set name = ?, description = ?, price = ? where id = ?;")) {
//            stmt.setString(1, product.getName());
//            stmt.setString(2, product.getDescription());
//            stmt.setBigDecimal(3, product.getPrice());
//            stmt.setLong(4, product.getId());
//            stmt.execute();
//        }
//    }
//
//    public void delete(long id) throws SQLException {
//        try (PreparedStatement stmt = conn.prepareStatement(
//                "delete from products where id = ?;")) {
//            stmt.setLong(1, id);
//            stmt.execute();
//        }
//    }
//
//    public Optional<Product> findById(long id) throws SQLException {
//        try (PreparedStatement stmt = conn.prepareStatement(
//                "select id, name, description, price from products where id = ?")) {
//            stmt.setLong(1, id);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return Optional.of(new Product(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4)));
//            }
//        }
//        return Optional.empty();
//    }

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

    private void createTableIfNotExists(Connection conn) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS products (\n" +
                "    id          INTEGER         AUTO_INCREMENT\n" +
                "                                UNIQUE\n" +
                "                                NOT NULL,\n" +
                "    name        VARCHAR (40)    UNIQUE\n" +
                "                                NOT NULL,\n" +
                "    description VARCHAR (255)   NOT NULL\n" +
                "                                DEFAULT (''),\n" +
                "    price       DECIMAL (12, 8) NOT NULL\n" +
                "                                DEFAULT (0),\n" +
                "    img         VARCHAR (54)    NOT NULL\n" +
                "                                DEFAULT ('resources/images/products/default.png'),\n" +
                "    category_id INTEGER         REFERENCES categories (id) ON DELETE CASCADE,\n" +
                "                                PRIMARY KEY (id)\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }
}
