package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.repositories.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

	@Autowired
	PageRepository pageRepository;

	@GetMapping(value = "/")
	public String getRoot() {
		return "redirect:/pages";
	}

	@GetMapping(value = "/pages")
	public String getPages(Model model) {
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
		
		return "page";
	}
}
