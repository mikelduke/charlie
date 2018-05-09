package com.mikelduke.charlie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class PostTemplateEngine {

    @Autowired
    TemplateEngine templateEngine;

    @Bean
    public TemplateEngine getTemplateEngine() {
        System.out.println("New Custom Template Engine");
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.addTemplateResolver(templateResolver);
        return templateEngine;
    }
}