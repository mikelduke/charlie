package com.mikelduke.charlie.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.mikelduke.charlie.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Service
public class GenerateService {

    @Autowired
    PostService postService;

    @Autowired
    PageService pageService;

    @Value("${charlie.generate.clean:false}")
    boolean clean;

    @Value("${charlie.generate.path:out}")
    String outPath;

    @Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@PostConstruct
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

    public String generate() {
        //TODO Find relative link solution for files . for same, .. for pages?
        long startTime = System.currentTimeMillis();
        clean();

        generatePage(null);

        pageService.findAll().forEach(this::generatePage);
        pageService.findAll().forEach(this::generatePosts);

        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");
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
        if (path == null || path == "") {
            path = "/";
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        path = path + "?render=true";

        try {
			return mvc.perform(MockMvcRequestBuilders.get(path))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
		} catch (Exception e) {
            throw new RuntimeException("error rendering page", e);
		}
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
