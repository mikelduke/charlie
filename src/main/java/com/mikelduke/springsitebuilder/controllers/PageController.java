package com.mikelduke.springsitebuilder.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PostRepository;
import com.mikelduke.springsitebuilder.services.MarkdownParseService;
import com.mikelduke.springsitebuilder.services.PageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Controller
public class PageController {

	@Autowired
	PageService pageService;

	@Autowired
	PostRepository postRepository;

	@Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Autowired
	MarkdownParseService mdParser;

	@GetMapping(value = "/pages")
	public String getPages(Model model,
			@RequestParam(name="new", required=false, defaultValue="false") boolean newPage) {
		if (newPage) {
			model.addAttribute("newPage", true);
			return "editPage";
		}

		Iterable<Page> pages = pageService.findAll();
		model.addAttribute("pages", pages);
		model.addAttribute("pagecount", pageService.count());
		
		return "pages";
	}

	@GetMapping(value = "/pages/{shortname}")
	public String getPage(Model model,
			@PathVariable String shortname) {
		//TODO Create page and post service to handle this
		Page page = pageService.findOneByShortName(shortname).orElseThrow(() -> new ResourceNotFoundException("page not found"));
		model.addAttribute("page", page);

		WebContext thContext = new WebContext(request, response, request.getServletContext(), request.getLocale());

		Iterable<Post> posts = postRepository.findAllByPage(page);
		posts.forEach(p -> {
			p.setContent(mdParser.render(p.getContent()));
			p.setContent(templateEngine.process(p.getContent(), thContext));
		});
		model.addAttribute("posts", posts);
		
		return "page";
	}

	@PostMapping(value = "/pages")
	public String newPage(@ModelAttribute("page") Page page) {
		pageService.save(page);
		return "redirect:/pages";
	}
}
