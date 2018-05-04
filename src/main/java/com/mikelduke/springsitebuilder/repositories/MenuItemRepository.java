package com.mikelduke.springsitebuilder.repositories;

import com.mikelduke.springsitebuilder.model.MenuItem;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, Long> {

}
