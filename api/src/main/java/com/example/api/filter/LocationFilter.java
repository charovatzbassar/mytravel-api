package com.example.api.filter;

import com.example.api.entity.Location;
import com.example.api.service.LocationService;
import com.example.api.utils.URLMethods;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LocationFilter implements Filter {

    private LocationService locationService;

    public LocationFilter() {}

    public LocationFilter(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String id = URLMethods.extractPathVariable(httpRequest.getRequestURI(), "locations");

        if (httpRequest.getMethod().equals("GET") || id.isEmpty()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        long locationId = Long.parseLong(id);

        Location location = locationService.getById(locationId);

        long userId = Long.parseLong((String) httpRequest.getAttribute("userId"));

        if (userId != location.getUser().getUserId()) {
            throw new RuntimeException("You are not allowed to alter this location!");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
