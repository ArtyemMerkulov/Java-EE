package com.artyemmerkylov.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class DBConnectionListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(DBConnectionListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application");

        ServletContext sc = sce.getServletContext();
        System.out.println("DBConnectionListener " + sc.getContextPath() + " DBConnectionListener");

        String url = sc.getInitParameter("url");
        String login = sc.getInitParameter("login");
        String password = sc.getInitParameter("password");

        try {
            Connection conn = DriverManager.getConnection(url, login, password);

            sc.setAttribute("connection", conn);
        } catch (SQLException ex) {
            logger.error("Error connection database");
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        Connection connection = (Connection) sc.getAttribute("connection");
        try {
            if (!(connection == null || connection.isClosed())) connection.close();
        } catch (SQLException ex) {
            logger.error("Error close connection", ex);
            logger.error(ex.getMessage());
        }
    }
}
