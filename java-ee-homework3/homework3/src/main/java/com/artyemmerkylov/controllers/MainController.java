package com.artyemmerkylov.controllers;

import com.artyemmerkylov.models.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "MainController", urlPatterns = {""})
public class MainController extends HttpServlet {

    private CategoryRepository categoryRepository;

    @Override
    public void init() throws ServletException {
        categoryRepository = (CategoryRepository) getServletContext().getAttribute("categoryRepository");
        if (categoryRepository == null) {
            throw new ServletException("Category repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("categories", categoryRepository.getAllCategories());
            getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
