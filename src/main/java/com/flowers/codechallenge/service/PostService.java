package com.flowers.codechallenge.service;

import com.flowers.codechallenge.model.Post;

import java.util.List;

public interface PostService {

    List<Post> getPosts();
    Long getUniqueUserCount();
    Post getUpdatedPost(Long id, Post post);
}
