package com.artyemmerkylov.controllers;

import com.artyemmerkylov.models.Cart;
import com.artyemmerkylov.models.OrderRepository;
import com.artyemmerkylov.models.Product;
import com.artyemmerkylov.models.ProductRepository;
import com.artyemmerkylov.utils.Pair;
import com.artyemmerkylov.utils.TotalPrice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CartController", urlPatterns = {"/cart/checkout", "/cart/add", "/cart"})
public class CartController extends HttpServlet {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private TotalPrice totalPrice;
    private Cart cart;

    @Override
    public void init() throws ServletException {
        orderRepository = (OrderRepository) getServletContext().getAttribute("orderRepository");
        if (orderRepository == null) {
            throw new ServletException("Order repository not initialized");
        }

        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (productRepository == null) {
            throw new ServletException("Order repository not initialized");
        }

        cart = (Cart) getServletContext().getAttribute("cart");
        if (cart == null) {
            throw new ServletException("Cart not initialized");
        }

        totalPrice = (TotalPrice) getServletContext().getAttribute("totalPrice");
        if (totalPrice == null) {
            throw new ServletException("Total Price not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqServletPath = req.getServletPath();

        if (reqServletPath.equals("/cart/add")) {
            Integer id = null;
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            cart.addProductParamsById(id);

            try {
                totalPrice.setTotalPrice(totalPrice.getTotalPrice().add(productRepository.getProductPriceById(id)));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            resp.sendRedirect(req.getHeader("referer"));
        } else if (reqServletPath.equals("/cart")) {

            List<Product> orderProducts = null;
            try {
                orderProducts = orderRepository.getProductsInCart(cart);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            List<Integer> amountOfOrderProducts = cart.getAmountOfProducts();

            Pair[] products = new Pair[orderProducts != null ? orderProducts.size() : 0];

            if (orderProducts != null)
                for (int i = 0; i < orderProducts.size(); i++)
                    products[i] = new Pair(orderProducts.get(i), amountOfOrderProducts.get(i));

            req.setAttribute("orderProducts", products);
            req.setAttribute("amountOfOrderProducts", amountOfOrderProducts);
            req.setAttribute("totalPrice", totalPrice);
            getServletContext().getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqServletPath = req.getServletPath();

        if (reqServletPath.equals("/cart/checkout")) {
            if (cart.getTotalProducts() > 0) {
                try {
                    orderRepository.createOrder(cart);
                    orderRepository.getProductsInCart(cart).forEach(product -> System.out.println("Prod: " + product.getId()));
                    cart.clear();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            resp.sendRedirect("/homework3");
        }
    }
}
