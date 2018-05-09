package com.mikelduke.charlie.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikelduke.charlie.model.Page;

public interface PageRepository extends PagingAndSortingRepository<Page, Long> {
    Optional<Page> findOneByShortName(String shortName);
}
