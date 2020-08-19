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
public class CategoryRepository implements Serializable {

    @Inject
    private ServletContext context;

    private Connection conn;

    public CategoryRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("connection");
        createTableIfNotExists(conn);
    }

    public List<Category> getAllCategories() throws SQLException {
        String query = "SELECT * FROM categories;";

        List<Category> res = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            try(ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    res.add(new Category(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)
                    ));
                }
            }
        }

        return res;
    }

    public List<Product> getAllProductsByCategoryName(String categoryName) throws SQLException {
        String query = "SELECT     products.id, products.name, products.description, products.price, products.img, categories.name, categories.name\n" +
                "       FROM       products\n" +
                "       INNER JOIN categories ON category_id=categories.id\n" +
                "       WHERE      categories.name=?;";

        List<Product> res = new ArrayList<>();

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoryName);

            try(ResultSet rs = pstmt.executeQuery()) {
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
        String query = "CREATE TABLE IF NOT EXISTS categories (\n" +
                "  id           int             NOT NULL AUTO_INCREMENT,\n" +
                "  name         varchar(40)     NOT NULL,\n" +
                "  description  varchar(255)    NOT NULL DEFAULT (''),\n" +
                "                               PRIMARY KEY (id),\n" +
                "                               UNIQUE KEY id (id),\n" +
                "                               UNIQUE KEY name (name)\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }

    public List<String> getAllCategoriesNames() throws SQLException {
        String query = "SELECT categories.name FROM categories ORDER BY id ASC;";

        List<String> res = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            try(ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
            }
        }

        return res;
    }

    public void insertCategory(Category category) throws SQLException {
        String query = "INSERT INTO categories(name, description) VALUES (?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            stmt.executeUpdate();
        }
    }

    public void updateCategory(Category category) throws SQLException {
        String query = "UPDATE categories SET name=?, description=? WHERE id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteCategory(Category category) throws SQLException {
        String query = "DELETE FROM categories WHERE id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, category.getId());

            stmt.executeUpdate();
        }
    }
}
