package com.mikelduke.springsitebuilder.controllers;

import com.mikelduke.springsitebuilder.model.MenuItem;
import com.mikelduke.springsitebuilder.repositories.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuItemController {

	@Autowired
	MenuItemRepository menuItemRepository;

    @GetMapping(value = "/menuitems")
	public Iterable<MenuItem> getMenuItems() {
		return menuItemRepository.findAll();
	}

	@GetMapping(value = "/menuitems/{id}")
	public MenuItem getMenuItem(@PathVariable("id") long id) {
        return menuItemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("menu item not found"));
	}
}
