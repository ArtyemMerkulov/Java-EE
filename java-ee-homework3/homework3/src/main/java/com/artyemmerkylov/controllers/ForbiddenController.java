package com.artyemmerkylov.controllers;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Для тестирования работы обработчика ошибки 403
@WebServlet(name = "ForbiddenController", urlPatterns = "/forbidden")
public class ForbiddenController extends HttpServlet {

    private final String viewsPath = "homework3/src/main/webapp/views/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] byteArray = Files.readAllBytes(Paths.get(viewsPath + "forbidden.jsp"));

        ServletOutputStream out = resp.getOutputStream();

        out.write(byteArray);

        req.getRequestDispatcher("/footer").include(req, resp);

        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
