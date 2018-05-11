package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.ExportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportController {

    @Autowired
    ExportService exportService;

    @GetMapping(value = "/export")
    public String generate(Model model) {
        exportService.export();

        return "redirect:/";
    }
}
