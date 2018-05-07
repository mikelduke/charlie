package com.mikelduke.springsitebuilder.services;

import java.util.Optional;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
	PostRepository postRepository;

    public Iterable<Post> findAllByPage(Page page) { 
        return postRepository.findAllByPage(page);
    }

    public Optional<Post> findOneByShortName(String shortName) {
        return postRepository.findOneByShortName(shortName);
    }

    public Post save(Post p) {
        return postRepository.save(p);
    }
}
