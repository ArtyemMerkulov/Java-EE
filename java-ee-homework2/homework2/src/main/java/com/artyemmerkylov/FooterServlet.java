package com.artyemmerkylov;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Как реализовать нормально, что бы можно было делать include,
// но нельзя было перейти по ссылке в браузере?
@WebServlet(name = "FooterServlet", urlPatterns = "/footer")
public class FooterServlet extends HttpServlet {

    private final String layoutsPath = "java-ee-homework2/homework2/src/main/webapp/layouts/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] byteArray = Files.readAllBytes(Paths.get(layoutsPath + "footer.jsp"));

        ServletOutputStream out = resp.getOutputStream();

        out.write(byteArray);
        out.flush();

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
