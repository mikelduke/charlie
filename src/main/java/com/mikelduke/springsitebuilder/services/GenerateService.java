package com.mikelduke.springsitebuilder.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mikelduke.springsitebuilder.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenerateService {

    @Autowired
    PostService postService;

    @Autowired
    PageService pageService;

    @Value("${sssg.generate.clean:false}")
    boolean clean;

    @Value("${sssg.generate.path:out}")
    String outPath;

    @Value("${server.port:8080}")
    String port;

    @Value("${sssg.generate.target-host:http://localhost}")
    String targetHost;

    public String generate() {
        //TODO Generate standalone post pages
        //TODO Find relative link solution for files . for same, .. for pages?
        clean();

        generatePage(null);

        pageService.findAll().forEach(this::generatePage);
        pageService.findAll().forEach(this::generatePosts);

        return "redirect:/";
    }

    private void clean() {
        File f = new File(outPath);

        if (clean) {
            System.out.println("Cleanup out path: " + f.getAbsolutePath());
            if (f.exists()) {
                f.delete();
            }
        } else {
            System.out.println("Skipping cleanup of " + f.getAbsolutePath());
        }
    }

    private String getPage(String path) {
        RestTemplate restTemplate = new RestTemplate();
        String url = targetHost + ":" + port + "/" + path + "?render=true";
        System.out.println("url: " + url);
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);

        return res.getBody();
    }

    private void savePage(String content, File f) throws IOException {
        f.getParentFile().mkdirs();
        f.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(content.getBytes());
        }
    }

    private void generatePage(Page page) {
        String pagePath = "";
        if (page != null) {
            pagePath = page.getShortName();
        }

        String content = getPage(pagePath);
        File f = new File(outPath + "/" + pagePath, "index.html");

        try {
            savePage(content, f);
        } catch (IOException e) {
            throw new RuntimeException("error saving page", e);
        }
    }

    private void generatePosts(Page page) {
        postService.findAllByPage(page).forEach((post) -> {
            String content = getPage(page.getShortName() + "/" + post.getShortName());
            File f = new File(outPath + "/" + page.getShortName() + "/" + post.getShortName(), 
                    "index.html");

            try {
                savePage(content, f);
            } catch (IOException e) {
                throw new RuntimeException("error saving post", e);
            }
        });
    }
}
