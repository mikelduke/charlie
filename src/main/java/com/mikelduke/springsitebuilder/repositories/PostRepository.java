package com.mikelduke.springsitebuilder.repositories;

import java.util.Optional;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.model.Post;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Iterable<Post> findAllByPage(Page page);
    Optional<Post> findOneByShortName(String shortName);
}
