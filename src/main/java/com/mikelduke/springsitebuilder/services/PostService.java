package com.mikelduke.springsitebuilder.services;

import java.util.Optional;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PostRepository;
import com.mikelduke.springsitebuilder.services.render.ContentRenderingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ContentRenderingService renderingService;

    public Iterable<Post> findAllByPage(Page page) {
        return postRepository.findAllByPage(page);
    }

    public Optional<Post> findOneByShortName(String shortName) {
        return postRepository.findOneByShortName(shortName);
    }

    public Post save(Post p) {
        return postRepository.save(p);
    }

    public Iterable<Post> renderAllByPage(Page page) {
        Iterable<Post> posts = postRepository.findAllByPage(page);

        posts.forEach(p -> {
            p.setContent(renderingService.render(p.getContent()));
        });

        return posts;
    }

    public Optional<Post> renderOneByShortName(String shortName) {
        Optional<Post> post = postRepository.findOneByShortName(shortName);
        post.ifPresent(p -> p.setContent(renderingService.render(p.getContent())));
        
        return post;
    }
}
