package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;
import com.mikelduke.springsitebuilder.repositories.PageRepository;
import com.mikelduke.springsitebuilder.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	@Autowired
	PageRepository pageRepository;

	@Autowired
	PostRepository postRepository;

	@GetMapping(value = "/pages")
	public String getPages(Model model,
			@RequestParam(name="new", required=false, defaultValue="false") boolean newPage) {
		if (newPage) {
			model.addAttribute("newPage", true);
			return "editPage";
		}

		Iterable<Page> pages = pageRepository.findAll();
		model.addAttribute("pages", pages);
		model.addAttribute("pagecount", pageRepository.count());
		
		return "pages";
	}

	@GetMapping(value = "/pages/{shortname}")
	public String getPage(Model model,
			@PathVariable String shortname) {
		Page page = pageRepository.findOneByShortName(shortname).orElseThrow(() -> new ResourceNotFoundException("page not found"));
		model.addAttribute("page", page);

		Iterable<Post> posts = postRepository.findAllByPage(page);
		model.addAttribute("posts", posts);
		
		return "page";
	}

	@PostMapping(value = "/pages")
	public String newPage(@ModelAttribute("page") Page page) {
		pageRepository.save(page);
		return "redirect:/pages";
	}
}
