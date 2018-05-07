package com.mikelduke.springsitebuilder.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.mikelduke.springsitebuilder.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class GenerateController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    HttpServletRequest req;

    @Autowired
    PageController pageController;

    @Value("${sssg.generate.path}")
    String path;

    @Value("${server.port:8080}")
    String port;

    @GetMapping(value = "/generate")
    public String generate(Model model) {
        //TODO Generate more pages, fix rel links to include .html extension
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/?render=true";
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
        String page = res.getBody();
        System.out.println(page);

        File dir = new File(path);
        System.out.println("Path: " + dir.getAbsolutePath());
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.out.println("Error creating path");
            }
        }
        File f = new File(path, "index.html");
        if (f.exists()) {
            f.delete();
        }
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(page.getBytes());
        } catch (IOException e) {
			e.printStackTrace();
		}

        return "redirect:/";
    }
}
