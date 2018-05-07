package com.mikelduke.springsitebuilder.services;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Autowired
	FlexmarkRenderService flexmarkRenderer;

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

        //TODO Create Rendering service
        WebContext thContext = new WebContext(request, response, request.getServletContext(), request.getLocale());
        
        posts.forEach(p -> {
            p.setContent(flexmarkRenderer.render(p.getContent()));
            p.setContent(templateEngine.process(p.getContent(), thContext));
        });

        return posts;
    }

    public Optional<Post> renderOneByShortName(String shortName) {
        Optional<Post> post = postRepository.findOneByShortName(shortName);
        if (post.isPresent()) {
            Post p = post.get();
            WebContext thContext = new WebContext(request, response, request.getServletContext(), request.getLocale());

            p.setContent(flexmarkRenderer.render(p.getContent()));
            p.setContent(templateEngine.process(p.getContent(), thContext));
        }
        
        return post;
    }
}
