package com.mikelduke.charlie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mikelduke.charlie.services.PostService;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    //TODO Implement this
}
