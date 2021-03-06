package com.mikelduke.charlie.config;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlexmarkConfiguration {

    @Bean
    public MutableDataSet getOptions() {
        MutableDataSet options = new MutableDataSet();

        return options;
    }

    @Bean
    public Parser getParser(@Autowired MutableDataSet options) {
        Parser parser = Parser.builder(options).build();

        return parser;
    }

    @Bean
    public HtmlRenderer getRenderer(
                @Autowired MutableDataSet options, 
                @Autowired Parser parser) {
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        return renderer;
    }

}
