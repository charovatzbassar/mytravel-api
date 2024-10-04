package com.example.api.config;

import com.example.api.filter.AuthFilter;
import com.example.api.filter.LocationFilter;
import com.example.api.service.JwtService;
import com.example.api.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    private final JwtService jwtService;
    private final LocationService locationService;

    @Autowired
    public FilterRegistrationConfig(JwtService jwtService, LocationService locationService) {
        this.jwtService = jwtService;
        this.locationService = locationService;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(){
        FilterRegistrationBean<AuthFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter(jwtService));
        registrationBean.addUrlPatterns("/api/locations/*");
        registrationBean.addUrlPatterns("/api/users/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LocationFilter> locationFilter() {
        FilterRegistrationBean<LocationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LocationFilter(locationService));
        registrationBean.addUrlPatterns("/api/locations/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
