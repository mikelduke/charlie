package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.GenerateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
