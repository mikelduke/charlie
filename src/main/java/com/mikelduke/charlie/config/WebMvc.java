package com.mikelduke.charlie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mikelduke.charlie.controllers.PageViewHandlerInterceptor;

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
