package com.mikelduke.charlie.repositories;

import java.util.Optional;

import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.model.Post;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Iterable<Post> findAllByPage(Page page);
    Iterable<Post> findAllByPage(Page page, Sort sort);
    Optional<Post> findOneByShortName(String shortName);
}
