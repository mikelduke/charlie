package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.model.MenuItem;
import com.mikelduke.charlie.repositories.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuItemController {

    @Autowired
    MenuItemRepository menuItemRepository;

    @GetMapping(value = "/menu")
    public String getPages(Model model,
            @RequestParam(name = "new", required = false, defaultValue = "false") boolean newMenuItem) {
        if (newMenuItem) {
            model.addAttribute("newMenuItem", true);
            return "editMenuItem";
        }

        Iterable<MenuItem> menuItems = menuItemRepository.findAll();
        model.addAttribute("menuItems", menuItems);

        return "menuitems";
    }

    @GetMapping(value = "/menu/{id}")
    public String editPage(Model model,
            @PathVariable Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("menu item not found"));
        model.addAttribute("menuItem", menuItem);

        return "editMenuItem";
    }

    @PostMapping(value = "/menu")
    public String newPage(@ModelAttribute("menuitem") MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return "redirect:/menuitems";
    }
}
