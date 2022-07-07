/**
 * This class implements PostService interface which contains methods
 * to fetch all Posts from third party service and calculates unique
 * UserIds in all Posts and also updates the nth object's data then
 * returns updated list of Posts back to the consumer
 *
 * @author Nagendra Kumar Aluru
 */

package com.flowers.codechallenge.service.impl;

import com.flowers.codechallenge.model.Post;
import com.flowers.codechallenge.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
    @Autowired
    WebClient webClient;


    /**
     * This method read all Posts from the third party service
     * and returns the same
     *
     * @return List<Post>
     */
    @Override
    public List<Post> getPosts() {
        LOGGER.info("Fetching all posts");

        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Post>>() {})
                .block();
    }

    /**
     * This method returns the unique userIds count
     *
     * @return count
     */
    @Override
    public Long getUniqueUserCount() {
        LOGGER.info("Counting unique UserIds");


         return this.getPosts().stream()
                .map(p -> p.getUserId())
                 .collect(Collectors.toList())
                 .stream()
                 .distinct()
                 .count();
    }


    /**
     * This method returns all Posts along with updated Post
     *
     * @return List<Post>
     */
    @Override
    public Post getUpdatedPost(Long index, Post mPost) {
        LOGGER.info("Fetching all posts and updating the contents of {} Post", index);


        if (index > this.getPosts().size()) {
            LOGGER.info("Requested array index {} is out of range!!", index);
            throw new ArrayIndexOutOfBoundsException("Requested array index is out of range!!");
        }

        AtomicInteger counter = new AtomicInteger();

        return this.getPosts()
                .stream()
                .map(post -> {
                    counter.getAndIncrement();
                    if (counter.get() == index) {
                        post.setTitle(mPost.getTitle());
                        post.setBody(mPost.getBody());
                    }
                    return post;
                }).collect(Collectors.toList()).get(index.intValue()-1);
    }
}
