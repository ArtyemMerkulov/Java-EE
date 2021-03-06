package com.artyemmerkylov.controllers;

import com.artyemmerkylov.models.CategoryRepository;
import com.artyemmerkylov.utils.TotalPrice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "MainController", urlPatterns = {""})
public class MainController extends HttpServlet {

    private CategoryRepository categoryRepository;
    private TotalPrice totalPrice;

    @Override
    public void init() throws ServletException {
        categoryRepository = (CategoryRepository) getServletContext().getAttribute("categoryRepository");
        if (categoryRepository == null) {
            throw new ServletException("Category repository not initialized");
        }

        totalPrice = (TotalPrice) getServletContext().getAttribute("totalPrice");
        if (totalPrice == null) {
            throw new ServletException("Total Price not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("categories", categoryRepository.getAllCategories());
            getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
