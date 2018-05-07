package com.mikelduke.springsitebuilder.services.render;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class FlexmarkRenderService implements Renderer {

    @Autowired 
    Parser parser;

    @Autowired
    HtmlRenderer renderer;

    public String render(String document) {
        Node node = parser.parse(document);
        String html = renderer.render(node);

        return html;
    }

}
