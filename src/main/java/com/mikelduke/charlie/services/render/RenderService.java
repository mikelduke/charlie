package com.mikelduke.charlie.services.render;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RenderService {

    @Autowired
    List<Renderer> renderers;

    public String render(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        String renderedContent = content;

        for (Renderer r : renderers) {
            renderedContent = r.render(renderedContent);
        }

        return renderedContent;
    }
}
