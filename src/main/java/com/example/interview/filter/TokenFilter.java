package com.example.interview.filter;

import com.example.interview.utils.Jwt;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "TokenFilter", urlPatterns = {"/user/*","/product/*","/cart/*"})
public class TokenFilter implements Filter {
    private static final String TOKEN_PARAM = "token";
    private static final String[] EXCLUDED_PATHS = {
            "/user/login",
            "/user/register",
            "/product/\\d+"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        for (String path : EXCLUDED_PATHS) {
            if (path.contains("\\d+")) {
                if (requestURI.matches("^" + path.replace("\\d+", "\\d+") + "$")) {
                    filterChain.doFilter(request, response);
                    return;
                }
            } else if (requestURI.startsWith(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        String token = request.getParameter(TOKEN_PARAM);
        if (token == null || token.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("未找到token");
            return;
        }
        if (!Jwt.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("无效token");
            return;
        }
        String userId = Jwt.getUserIdFromToken(token);
        request.setAttribute("uid", userId);
        filterChain.doFilter(request, response);
    }
}