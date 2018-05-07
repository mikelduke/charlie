package com.mikelduke.springsitebuilder.services.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Service
@Order(2)
public class ThymeleafRenderService implements Renderer {

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    public String render(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        String renderedContent = content;

        WebContext thContext = new WebContext(request, response, 
                request.getServletContext(), request.getLocale());

        renderedContent = templateEngine.process(renderedContent, thContext);

        return renderedContent;
    }
}
