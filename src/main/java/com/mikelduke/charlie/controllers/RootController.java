package com.mikelduke.charlie.controllers;

import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.model.Post;
import com.mikelduke.charlie.services.PageService;
import com.mikelduke.charlie.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

        if (page != null) {
            Iterable<Post> posts = postService.renderAllByPage(page);
            model.addAttribute("posts", posts);
            return page.getLayout();
        } else {
            return Page.DEFAULT_LAYOUT;
        }

    }

    @GetMapping(value = "/{shortname}")
    public String getPage(Model model,
            @PathVariable String shortname) {
        Page page = pageService.findOneByShortName(shortname)
                .orElseThrow(() -> new ResourceNotFoundException("page not found"));
        model.addAttribute("page", page);

        Iterable<Post> posts = postService.renderAllByPage(page);
        model.addAttribute("posts", posts);

        return page.getLayout();
    }

    @GetMapping(value = "/{shortname}/{postShortname}")
    public String getPostForPage(Model model, 
            @PathVariable String shortname, 
            @PathVariable String postShortname) {
        Post post = postService.renderOneByShortName(postShortname)
                .orElseThrow(() -> new ResourceNotFoundException("post not found"));
        model.addAttribute("post", post);

        return post.getLayout();
    }
}
