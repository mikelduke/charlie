package com.mikelduke.charlie.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikelduke.charlie.model.MenuItem;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, Long> {

}
