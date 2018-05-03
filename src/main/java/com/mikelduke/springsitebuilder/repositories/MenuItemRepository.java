package com.mikelduke.springsitebuilder.repositories;

import com.mikelduke.springsitebuilder.model.MenuItem;

import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {

}
