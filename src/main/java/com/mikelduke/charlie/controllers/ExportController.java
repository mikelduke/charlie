package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.ExportService;
import com.mikelduke.charlie.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportController {

    @Autowired
    ExportService exportService;

    @Autowired
    MessageService messages;

    @GetMapping(value = "/export")
    public String generate(Model model) {
        long startTime = System.currentTimeMillis();
        exportService.export();

        messages.addMessage("Export Site in " + (System.currentTimeMillis() - startTime) + "ms");
        return "redirect:/";
    }
}
