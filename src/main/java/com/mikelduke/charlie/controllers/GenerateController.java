package com.mikelduke.charlie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mikelduke.charlie.services.GenerateService;

@Controller
public class GenerateController {

    @Autowired
    GenerateService generateService;

    @GetMapping(value = "/generate")
    public String generate(Model model) {
        generateService.generate();

        return "redirect:/";
    }
}
