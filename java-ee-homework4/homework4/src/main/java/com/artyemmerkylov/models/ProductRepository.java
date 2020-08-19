package com.artyemmerkylov.models;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class ProductRepository implements Serializable {

    @Inject
    private ServletContext context;

    private Connection conn;

    private final String DEFAULT_IMG = "'resources/images/products/default.png'";

    public ProductRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("connection");
        createTableIfNotExists(conn);
    }

    public List<Product> getProductsByIds(List<Integer> productsIds) throws SQLException {
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
                "       INNER JOIN categories ON category_id=categories.id\n" +
                "       ORDER BY products.id ASC;";

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
                "  img          varchar(54)     NOT NULL DEFAULT (" + DEFAULT_IMG + "),\n" +
                "  category_id  int             DEFAULT NULL,\n" +
                "                               PRIMARY KEY (id),\n" +
                "                               UNIQUE KEY id (id),\n" +
                "                               UNIQUE KEY name (name),\n" +
                "                               KEY fk_categories_products (category_id),\n" +
                "                               CONSTRAINT fk_categories_products FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }

    public void insertProduct(Product product) throws SQLException {
        String query = "INSERT INTO products(name, description, price, img, category_id) VALUES (?, ?, ?, ?, (SELECT id FROM categories WHERE name=?));";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setString(4, product.getImg());
            stmt.setString(5, product.getCategoryName());

            stmt.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products\n" +
                "       SET    name=?, description=?, price=?, img=?, category_id=(SELECT id FROM categories WHERE name=?)\n" +
                "       WHERE  id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setString(4, product.getImg());
            stmt.setString(5, product.getCategoryName());
            stmt.setInt(6, product.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteProduct(Product product) throws SQLException {
        String productOrdersQuery = "DELETE FROM product_orders WHERE product_id=?;";
        String productsQuery = "DELETE FROM products WHERE id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(productOrdersQuery)) {
            stmt.setInt(1, product.getId());

            stmt.executeUpdate();
        }

        try (PreparedStatement stmt = conn.prepareStatement(productsQuery)) {
            stmt.setInt(1, product.getId());

            stmt.executeUpdate();
        }
    }
}
