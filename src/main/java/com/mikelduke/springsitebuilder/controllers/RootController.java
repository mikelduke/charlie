package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.services.PageService;
import com.mikelduke.springsitebuilder.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @Autowired
    PageService pageService;

    @Autowired
    PostService postService;

    @GetMapping(value = "/")
    public String getRoot(Model model) {
        Page page = pageService.findHomePage();
        model.addAttribute("page", page);

        Iterable<Post> posts = postService.renderAllByPage(page);
        model.addAttribute("posts", posts);

        return "page";
    }
}
