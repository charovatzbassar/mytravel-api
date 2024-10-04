package com.example.api.filter;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.api.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class AuthFilter implements Filter {

    private JwtService jwtService;

    public AuthFilter() {}

    public AuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (httpRequest.getHeader("Authentication").isEmpty()) throw new RuntimeException("Unauthorized");

        String token = httpRequest.getHeader("Authentication");

        this.jwtService.validateToken(token);

        DecodedJWT decodedJWT = this.jwtService.decodeToken(token);

        Claim claim = decodedJWT.getClaim("user_id");

        httpRequest.setAttribute("userId", claim.toString());

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
