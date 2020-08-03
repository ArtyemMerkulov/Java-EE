package com.artyemmerkylov.errors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");

        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");

        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) requestUri = "Unknown";

        PrintWriter out = resp.getWriter();

        out.println("<html>\n" +
                "   <head><title>Error info</title></head>\n" +
                "   <body>\n" +
                "       <h1>Exception Type: " + throwable.getClass().getName() + "</h1>\n" +
                "       <h2>Error information:</h2>\n" +
                "       <div>The status code: " + statusCode + "</div>\n" +
                "       <div>The request URI: " + requestUri + "</div>\n" +
                "       <div>The exception message: " + throwable.getMessage() + "</div>\n" +
                "       <div>" + System.getProperty("user.dir") + "</div>\n" +
                "    </body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
