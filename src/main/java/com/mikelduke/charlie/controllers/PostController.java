package com.mikelduke.charlie.controllers;

import java.time.Instant;
import java.util.Date;

import com.mikelduke.charlie.model.Post;
import com.mikelduke.charlie.services.PageService;
import com.mikelduke.charlie.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PageService pageService;

    // @GetMapping("/posts/{pageShortname}/{postShortname}")
    // public String getPostForPage(Model model, 
    //         @PathVariable String pageShortname, 
    //         @PathVariable String postShortname) {
    //     Post post = postService.renderOneByShortName(postShortname)
    //             .orElseThrow(() -> new ResourceNotFoundException("post not found"));
    //     model.addAttribute("post", post);

    //     return "post";
    // }

    @GetMapping("/posts")
    public String getPosts(Model model,
            @RequestParam(name = "new", required = false, defaultValue = "false") boolean newPost) {
        if (newPost) {
            model.addAttribute("newPost", true);
            model.addAttribute("pages", pageService.findAll());
            return "editPost";
        }

        Iterable<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("postCount", postService.count());

        return "posts";
    }

    @PostMapping("/posts")
    public String savePost(@ModelAttribute("post") Post post) {
        post.setDate(Date.from(Instant.now()));
        postService.save(post);

        return "redirect:/" + post.getPage().getShortName() + "/" + post.getShortName();
    }
}
