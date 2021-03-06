package com.artyemmerkylov.controllers;

import com.artyemmerkylov.models.ProductRepository;
import com.artyemmerkylov.utils.TotalPrice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "ProductController", urlPatterns = {"/product/*"})
public class ProductController extends HttpServlet {

    private ProductRepository productRepository;
    private TotalPrice totalPrice;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (productRepository == null) {
            throw new ServletException("Product repository not initialized");
        }

        totalPrice = (TotalPrice) getServletContext().getAttribute("totalPrice");
        if (totalPrice == null) {
            throw new ServletException("Total Price not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] uriParts = req.getRequestURI().split("/");
            Integer id = Integer.parseInt(uriParts[uriParts.length - 1]);

            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("product", productRepository.getProductById(id));
            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}