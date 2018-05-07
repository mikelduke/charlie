package com.mikelduke.springsitebuilder.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikelduke.springsitebuilder.repositories.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        if (modelAndView != null) {
            modelAndView.addObject("menuItems", 
                    menuItemRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));

            String render = request.getParameter("render");
            try {
                Boolean renderB = Boolean.parseBoolean(render);
                modelAndView.addObject("render", renderB);
            } catch (Exception e) {
                modelAndView.addObject("render", false);
            }
        }
    }
}
