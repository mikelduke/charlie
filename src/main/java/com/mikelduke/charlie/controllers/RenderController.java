package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.render.RenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RenderController {

    @Autowired
    RenderService renderService;

    @PostMapping(value = "/render", consumes={"text/plain"})
    @ResponseBody
    public String generate(Model model,
            @RequestBody String rawString) {
        System.out.println("received: " + rawString);
        String result = renderService.render(rawString);
        System.out.println("return: " + result);
        return result;
    }
}
