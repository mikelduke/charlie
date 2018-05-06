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

				Post post2 = Post.builder()
						.content("post contents!!!!! <br /> second part! <a href='https://google.com'>a link</a>")
						.shortName("post-2")
						.title("the second post")
						.createdAtMs(Instant.now().toEpochMilli()).build();

				List<Post> posts = new ArrayList<>();
				posts.add(post);
				posts.add(post2);

				Page page = Page.builder()
						.desc("page 1 desc")
						.name("page 1 name")
						.shortName("p1")
						.build();
				
				pageRepo.save(page);
				post.setPage(page);
				postRepo.save(post);

				post2.setPage(page);
				postRepo.save(post2);

				MenuItem menuItem = MenuItem.builder()
						.text("google")
						.target("https://www.google.com")
						.position(1)
						.relative(false)
						.build();

				menuRepo.save(menuItem);

				MenuItem menuItem2 = MenuItem.builder()
						.text("slashdot")
						.target("https://slashdot.org")
						.position(2)
						.relative(false)
						.build();

				menuRepo.save(menuItem2);

				MenuItem menuItem3 = MenuItem.builder()
						.text(page.getName())
						.target(page.getLink())
						.position(3)
						.relative(true)
						.build();

				menuRepo.save(menuItem3);
			}
		};
	}
}
