package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.services.PageService;
import com.mikelduke.charlie.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    PageService pageService;

    @Autowired
    PostService postService;

    @GetMapping(value = "/pages")
    public String getPages(Model model,
            @RequestParam(name = "new", required = false, defaultValue = "false") boolean newPage) {
        if (newPage) {
            model.addAttribute("newPage", true);
            return "editPage";
        }

        Iterable<Page> pages = pageService.findAll();
        model.addAttribute("pages", pages);
        model.addAttribute("pagecount", pageService.count());

        return "pages";
    }

    @PostMapping(value = "/pages")
    public String newPage(@ModelAttribute("page") Page page) {
        pageService.save(page);
        return "redirect:/pages";
    }

    @PostMapping("/pages/{pageShortName}/delete")
    public String deletePage(@PathVariable String pageShortName) {
        pageService.deletePageByPageShortName(pageShortName);
        return "redirect:/pages";
    }
}
