package com.mikelduke.springsitebuilder;

import java.time.Instant;

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
				
				Page page = Page.builder()
						.desc("page 1 desc")
						.name("page 1 name")
						.shortName("p1")
						.build();
						
				pageRepo.save(page);

				Post post = Post.builder()
						.content("post body")
						.shortName("post-1")
						.title("the first post")
						.page(page)
						.createdAtMs(Instant.now().toEpochMilli()).build();
				postRepo.save(post);

				Post post2 = Post.builder()
						.content("post contents!!!!! <br /> second part! <a href='https://google.com'>a link</a>")
						.shortName("post-2")
						.title("the second post")
						.page(page)
						.createdAtMs(Instant.now().toEpochMilli()).build();
				postRepo.save(post2);

				Post post3 = Post.builder()
						.content("post 3<br /><a th:href='@{http://www.reddit.com}'>test link</a><br /><div th:replace='fragments/menu :: menu'></div>")
						.shortName("post-3")
						.title("the third post testing thymeleaf templates in content")
						.page(page)
						.createdAtMs(Instant.now().toEpochMilli()).build();
				postRepo.save(post3);

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
