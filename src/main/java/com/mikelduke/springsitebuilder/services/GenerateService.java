package com.mikelduke.springsitebuilder.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenerateService {

    @Autowired
    PostService postService;

    @Value("${sssg.generate.path}")
    String path;

    @Value("${server.port:8080}")
    String port;

    public String generate() {
        //TODO generate more page
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
