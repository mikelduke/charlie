package com.mikelduke.springsitebuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mikelduke.springsitebuilder.model.MenuItem;
import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.MenuItemRepository;
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

	@Autowired
	MenuItemRepository menuRepo;
	
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
						.build();
				
				pageRepo.save(page);
				post.setPage(page);
				postRepo.save(post);

				MenuItem menuItem = MenuItem.builder()
						.text("google")
						.target("www.google.com")
						.position(1)
						.build();

				menuRepo.save(menuItem);

				MenuItem menuItem2 = MenuItem.builder()
						.text("slashdot")
						.target("slashdot.org")
						.position(2)
						.build();

				menuRepo.save(menuItem2);

				MenuItem menuItem3 = MenuItem.builder()
						.text(page.getName())
						.target(page.getLink())
						.position(3)
						.build();

				menuRepo.save(menuItem3);
			}
		};
	}
}
