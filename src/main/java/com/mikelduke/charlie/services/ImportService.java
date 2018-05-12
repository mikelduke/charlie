package com.mikelduke.charlie.services;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImportService {

    @Value("${charlie.import.path:in}")
    String path;

    @Value("${charlie.import.extension:yaml}")
    String extension;

    @Autowired
    PostService postService;

    @Autowired
    PageService pageService;

    @Autowired
    ObjectMapper mapper;

    public void importProject() {
        File importDir = new File(path);
        System.out.println("Importing project from " + importDir.getAbsolutePath());
        //TODO Implement this
    }
}
