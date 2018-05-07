package com.mikelduke.springsitebuilder.config;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkdownConfiguration {

    @Bean
    public MutableDataSet getOptions() {
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        // options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(),
        // StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        // options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

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

        // Node document = parser.parse("This is *Sparta*");
        // String html = renderer.render(document); // "<p>This is <em>Sparta</em></p>\n"

        return renderer;
    }

}
