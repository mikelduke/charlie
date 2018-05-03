package com.mikelduke.springsitebuilder.repositories;

import com.mikelduke.springsitebuilder.model.Post;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}
