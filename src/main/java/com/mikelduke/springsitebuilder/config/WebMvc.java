package com.mikelduke.springsitebuilder.config;

import com.mikelduke.springsitebuilder.controllers.PageViewHandlerInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@EnableWebMvc
public class WebMvc implements WebMvcConfigurer {

    @Autowired
    PageViewHandlerInterceptor pvhi;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pvhi);
    }
}
