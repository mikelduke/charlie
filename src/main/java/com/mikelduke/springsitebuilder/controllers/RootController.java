package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.repositories.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

	@Autowired
    PageRepository pageRepository;

    @Value("${sssg.homepage.shortname:null}")
    String defaultPage;

	@GetMapping(value = "/")
	public String getRoot(Model model) {
        Page page = pageRepository
                .findOneByShortName(defaultPage)
                .orElse(
                    pageRepository.findAll(
                        PageRequest.of(0, 1, Sort.Direction.ASC, "id"))
                        .stream().findFirst().orElse(null));
		model.addAttribute("page", page);
		
		return "page";
	}
}
