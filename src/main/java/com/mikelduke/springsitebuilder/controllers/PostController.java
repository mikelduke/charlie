package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/posts/{shortname}")
    public String getPost(Model model, @PathVariable String shortname) {
        Post post = postRepository.findOneByShortName(shortname)
                .orElseThrow(() -> new ResourceNotFoundException("page not found"));
        model.addAttribute("post", post);

        return "post";
    }
}
