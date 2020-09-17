package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.Position;
import com.artyemmerkylov.models.Product;
import com.artyemmerkylov.service.CartService;
import com.artyemmerkylov.service.ProductRepr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@SessionScoped
@Named
public class AdminCartController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(AdminCartController.class);

    @EJB
    private CartService cartService;

    public List<Position> getAllPositions() {
        logger.info("User checked cart");
        return cartService.getAllPositions();
    }

    public void addToCart(ProductRepr productRepr) {
        logger.info("User add product with id = " + productRepr.getId() + " to cart.");
        cartService.add(productRepr);
    }

    public void deletePosition(Integer id) {
        logger.info("User delete product with id = " + id + " from cart");
        cartService.delete(id);
    }

    public BigDecimal getTotalPrice() {
        return cartService.getTotalPrice();
    }
}
