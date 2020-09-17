package com.artyemmerkylov.models;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class PurchaseRepository {

    @Inject
    private ServletContext context;

    private Connection conn;

    public PurchaseRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("connection");
        createTableIfNotExists(conn);
    }

    public List<Purchase> getPurchaseListByOrderId(Integer id) throws SQLException {
        String query = "SELECT      products.id, products.name, products.description, products.price, products.img,\n" +
                "                   categories.name, product_orders.total\n" +
                "       FROM        product_orders\n" +
                "       INNER JOIN  products    ON  product_orders.product_id   =   products.id\n" +
                "       INNER JOIN  categories  ON  products.category_id        =   categories.id\n" +
                "       WHERE       product_orders.order_id=?;";

        List<Purchase> purchaseList = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    purchaseList.add(new Purchase(
                            new Product(
                                    rs.getInt(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP),
                                    rs.getString(5),
                                    1
                            ),
                            rs.getInt(7)
                    ));
                }
            }
        }

        return purchaseList;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS product_orders (\n" +
                "  id           int             NOT NULL AUTO_INCREMENT,\n" +
                "  order_id     int     NOT NULL,\n" +
                "  product_id   int     NOT NULL,\n" +
                "  total        int     NOT NULL,\n" +
                "                       PRIMARY KEY (id),\n" +
                "                       UNIQUE KEY id (id),\n" +
                "                       KEY fk_product (product_id),\n" +
                "                       KEY fk_order (order_id),\n" +
                "                       CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "                       CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        }
    }
}
