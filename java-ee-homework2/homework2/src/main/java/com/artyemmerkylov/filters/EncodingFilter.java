package com.artyemmerkylov.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
