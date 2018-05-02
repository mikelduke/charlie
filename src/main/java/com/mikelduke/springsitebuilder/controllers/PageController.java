package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.repositories.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	@Autowired
	PageRepository pageRepository;

	@GetMapping(value = "/")
	public String getRoot() {
		return "redirect:/pages";
	}

	@GetMapping(value = "/pages")
	public String getPages(Model model,
			@RequestParam(value="render", defaultValue="false", required=false) boolean render) {
		model.addAttribute("render", render);

		Iterable<Page> pages = pageRepository.findAll();
		model.addAttribute("pages", pages);
		model.addAttribute("pagecount", pageRepository.count());
		
		return "pages";
	}

	@GetMapping(value = "/pages/{id}")
	public String getPage(Model model,
			@RequestParam(value="render", defaultValue="false", required=false) boolean render,
			@PathVariable long id) {
		model.addAttribute("render", render);
		
		Page page = pageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("page not found"));
		model.addAttribute("page", page);
		
		return "page";
	}
}
