package com.mikelduke.charlie.services;

import java.util.Optional;

import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.model.Post;
import com.mikelduke.charlie.repositories.PostRepository;
import com.mikelduke.charlie.services.render.RenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    RenderService renderingService;

    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    public Iterable<Post> findAllByPage(Page page) {
        return postRepository.findAllByPage(page);
    }

    public Optional<Post> findOneByShortName(String shortName) {
        return postRepository.findOneByShortName(shortName);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post p) {
        return postRepository.save(p);
    }

    public Iterable<Post> renderAllByPage(Page page) {
        Direction dir = Direction.DESC;
        if (!page.isNewestFirst()) {
            dir = Direction.ASC;
        }

        Iterable<Post> posts = postRepository.findAllByPage(page, Sort.by(dir, "date"));

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

    public long count() {
        return postRepository.count();
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
