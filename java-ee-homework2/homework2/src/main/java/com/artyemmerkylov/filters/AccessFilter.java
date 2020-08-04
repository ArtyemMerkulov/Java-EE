package com.artyemmerkylov.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/forbidden", "/footer", "/NotFoundError",
        "/ForbiddenError", "/ErrorHandler"})
public class AccessFilter implements Filter {

    private final String appPath = "/homework2";

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        String reqUri = ((HttpServletRequest) req).getRequestURI();
        
        if (reqUri.startsWith(appPath + "/forbidden")) {
            req.setAttribute("status_code", 403);
            req.setAttribute("uri", appPath + "/forbidden");

            req.getRequestDispatcher("/ForbiddenError").forward(req, res);
        } else if (reqUri.startsWith(appPath + "/footer") ||
                reqUri.startsWith(appPath + "/NotFoundError") ||
                reqUri.startsWith(appPath + "/ForbiddenError") ||
                reqUri.startsWith(appPath + "/ErrorHandler")) {
            req.setAttribute("javax.servlet.error.status_code", 404);
            req.setAttribute("javax.servlet.error.request_uri", reqUri);

            req.getRequestDispatcher("/NotFoundError").forward(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
