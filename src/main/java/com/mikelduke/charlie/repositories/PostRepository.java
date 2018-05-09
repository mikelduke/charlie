package com.mikelduke.charlie.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikelduke.charlie.model.Page;
import com.mikelduke.charlie.model.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Iterable<Post> findAllByPage(Page page);
    Optional<Post> findOneByShortName(String shortName);
}
