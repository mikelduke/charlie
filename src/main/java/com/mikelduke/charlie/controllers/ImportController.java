package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.services.ImportService;
import com.mikelduke.charlie.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImportController {

    @Autowired
    ImportService importService;

    @Autowired
    MessageService messages;

    @GetMapping(value = "/import")
    public String generate(Model model) {
        long start = System.currentTimeMillis();
        importService.importProject();

        messages.addMessage("Import site in " + (System.currentTimeMillis() - start) + "ms");
        return "redirect:/";
    }
}
