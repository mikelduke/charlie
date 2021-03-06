package com.mikelduke.charlie;

import java.time.Instant;
import java.util.Date;

import com.mikelduke.charlie.model.MenuItem;
import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.model.Post;
import com.mikelduke.charlie.repositories.MenuItemRepository;
import com.mikelduke.charlie.services.PageService;
import com.mikelduke.charlie.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TestDataGenerator {
	
	@Value("${charlie.testdata.generate:false}")
	boolean generateTestData;

	@Autowired
	PageService pageService;

	@Autowired
	PostService postService;

	@Autowired
	MenuItemRepository menuRepo;
	
	@Bean
	@Order(10)
	public ApplicationRunner generateTestUsers() {
		return (args) -> {
			if (generateTestData) {
				System.out.println("Generating Test Data");
				
				Page page = Page.builder()
						.desc("page 1 desc")
						.name("page 1 name")
						.shortName("p1")
						.build();
						
				pageService.save(page);

				Post post = Post.builder()
						.content("post body")
						.shortName("post-1")
						.title("the first post")
						.page(page)
						.date(Date.from(Instant.now())).build();
				postService.save(post);

				Post post2 = Post.builder()
						.content("post contents!!!!! <br /> second part! <a href='https://google.com'>a link</a>")
						.shortName("post-2")
						.title("the second post")
						.page(page)
						.date(Date.from(Instant.now())).build();
				postService.save(post2);

				Post post3 = Post.builder()
						.content("post 3<br /><a th:href='@{http://www.reddit.com}'>test link</a><br /><div th:replace='fragments/menu :: menu'></div>")
						.shortName("post-3")
						.title("the third post testing thymeleaf templates in content")
						.page(page)
						.date(Date.from(Instant.now())).build();
				postService.save(post3);

				Post post4 = Post.builder()
						.content("* test markdown\n* line two")
						.shortName("post-4")
						.title("markdown post content")
						.page(page)
						.date(Date.from(Instant.now())).build();
				postService.save(post4);

				MenuItem menuItem = MenuItem.builder()
						.text("google")
						.target("https://www.google.com")
						.position(1)
						.build();
				menuRepo.save(menuItem);

				MenuItem menuItem2 = MenuItem.builder()
						.text("slashdot")
						.target("https://slashdot.org")
						.position(2)
						.build();
				menuRepo.save(menuItem2);

				MenuItem menuItem3 = MenuItem.builder()
						.text(page.getName())
						.target(page.getShortName())
						.position(3)
						.build();
				menuRepo.save(menuItem3);
			}
		};
	}
}
