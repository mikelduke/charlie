package com.mikelduke.springsitebuilder.repositories;

import java.util.Optional;

import com.mikelduke.springsitebuilder.model.Page;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageRepository extends PagingAndSortingRepository<Page, Long> {
    Optional<Page> findOneByShortName(String shortName);
}
