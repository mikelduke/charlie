package com.mikelduke.springsitebuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PageRepository;
import com.mikelduke.springsitebuilder.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TestDataGenerator {
	
	@Value("${sssg.testdata.generate:false}")
	boolean generateTestData;

	@Autowired
	PageRepository pageRepo;

	@Autowired
	PostRepository postRepo;
	
	@Bean
	@Order(1)
	public CommandLineRunner generateTestUsers() {
		return (args) -> {
			if (generateTestData) {
				System.out.println("Generating Test Data");

				Post post = Post.builder()
						.content("post body")
						.shortName("post-1")
						.title("the first post")
						.createdAtMs(Instant.now().toEpochMilli()).build();

				List<Post> posts = new ArrayList<>();
				posts.add(post);

				Page page = Page.builder()
						.desc("page 1 desc")
						.name("page 1 name")
						.shortName("p1")
						.posts(posts)
						.build();
				
				pageRepo.save(page);
			}
		};
	}
}
