package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.ImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImportController {

    @Autowired
    ImportService importService;

    @GetMapping(value = "/import")
    public String generate(Model model) {
        importService.importProject();

        return "redirect:/";
    }
}
