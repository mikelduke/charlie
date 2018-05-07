package com.mikelduke.springsitebuilder.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Service
public class ContentRenderingService {

    @Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Autowired
	FlexmarkRenderService flexmarkRenderer;

    public String render(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        
        String renderedContent = "";

        WebContext thContext = new WebContext(request, response, request.getServletContext(), request.getLocale());
        
        renderedContent = flexmarkRenderer.render(content);
        renderedContent = templateEngine.process(renderedContent, thContext);

        return renderedContent;
    }
}
