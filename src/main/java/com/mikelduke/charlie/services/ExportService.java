package com.mikelduke.charlie.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikelduke.charlie.model.MenuItem;
import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.model.Post;
import com.mikelduke.charlie.repositories.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

@Service
public class ExportService {

    @Autowired
    PostService postService;

    @Autowired
    PageService pageService;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Value("${charlie.export.clean:false}")
    boolean clean;

    @Value("${charlie.import.path:in}")
    String outPath;

    @Value("${charlie.import.extension:yaml}")
    String extension;

    @Value("${charlie.export.content-as-separate-doc:true}")
    boolean exportContentAsSeparateDoc;

    @Autowired
    ObjectMapper mapper;

    public String export() {
        long startTime = System.currentTimeMillis();
        clean();

        pageService.findAll().forEach(this::exportPage);
        postService.findAll().forEach(this::exportPost);

        exportMenuItems(menuItemRepository.findAll());

        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");
        return "redirect:/";
    }

    private void clean() {
        File f = new File(outPath);

        if (clean) {
            System.out.println("Cleanup import path: " + f.getAbsolutePath());
			System.out.println("Deleted: " + FileSystemUtils.deleteRecursively(f));
        } else {
            System.out.println("Skipping cleanup of " + f.getAbsolutePath());
        }
    }

    private void exportPage(Page page) {
        try {
            System.out.println("Saving page " + page.getShortName());
            String output = mapper.writeValueAsString(page);

            File f = new File(outPath + "/" + page.getShortName() + "." + extension);
            saveToFile(f, output);
		} catch (IOException e) {
			throw new RuntimeException("Error saving json", e);
		}
    }

    private void exportPost(Post post) {
        Post postCopy = new Post(post.getId(), post.getTitle(), 
                post.getShortName(), post.getContent(), post.getCreatedAtMs(), null);
        try {
            System.out.println("Saving post: " + post.getPage().getShortName() + "/" + post.getShortName());
            String output = "";
            if (exportContentAsSeparateDoc) {
                String content = postCopy.getContent();
                postCopy.setContent(null);
                StringBuilder sb = new StringBuilder();
                sb.append(mapper.writeValueAsString(postCopy));
                sb.append("---");
                sb.append(System.lineSeparator());
                sb.append(content);
                // output += mapper.writeValueAsString(content);
                output = sb.toString();
            } else {
                output = mapper.writeValueAsString(postCopy);
            }

            File f = new File(outPath + "/" + post.getPage().getShortName() 
                    + "/" + post.getShortName() + "." + extension);
            saveToFile(f, output);
		} catch (IOException e) {
			throw new RuntimeException("Error saving json", e);
		}
    }

    private void exportMenuItems(Iterable<MenuItem> menuItems) {
		try {
            String content = mapper.writeValueAsString(menuItems);
            File f = new File(outPath + "/" + "menuItems" + "." + extension);
            saveToFile(f, content);
		} catch (Exception e) {
			throw new RuntimeException("Error saving menu items", e);
		}
    }

    private void saveToFile(File f, String content) throws IOException {
        f.getParentFile().mkdirs();
        f.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(content.getBytes());
        }
    }
}
