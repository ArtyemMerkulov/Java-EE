package com.artyemmerkylov.errors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ForbiddenError", urlPatterns = "/ForbiddenError")
public class ForbiddenError extends HttpServlet {
    private static final long serialVersionUID = 2L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer statusCode = (Integer) req.getAttribute("status_code");

        String requestUri = (String) req.getAttribute("uri");
        if (requestUri == null) requestUri = "Unknown";

        PrintWriter out = resp.getWriter();

        out.println("<html>\n" +
                "   <head><title>Error info</title></head>\n" +
                "   <body>\n" +
                "       <h2>Error information:</h2>\n" +
                "       <div>The status code: " + statusCode + "</div>\n" +
                "       <div>Access is denied to " + requestUri + "</div>\n" +
                "    </body>\n" +
                "</html>");

        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
