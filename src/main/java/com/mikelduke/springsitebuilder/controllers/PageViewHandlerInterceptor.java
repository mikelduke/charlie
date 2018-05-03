package com.mikelduke.springsitebuilder.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikelduke.springsitebuilder.repositories.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class PageViewHandlerInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	MenuItemRepository menuItemRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("Add menu items");

        if (modelAndView != null) {
            modelAndView.addObject("menuItems", menuItemRepository.findAll());
        }
    }
}
