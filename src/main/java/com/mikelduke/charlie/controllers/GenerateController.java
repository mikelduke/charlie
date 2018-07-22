package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.GenerateService;
import com.mikelduke.charlie.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenerateController {

    @Autowired
    GenerateService generateService;

    @Autowired
    MessageService messages;

    @GetMapping(value = "/generate")
    public String generate(Model model) {
        long start = System.currentTimeMillis();
        generateService.generate();
        messages.addMessage("Site generated in " + (System.currentTimeMillis() - start) + "ms");

        return "redirect:/";
    }
}
