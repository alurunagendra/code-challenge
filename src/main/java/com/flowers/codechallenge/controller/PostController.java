/**
 * This controller class has two REST endpoints exposed to
 * handle 'Get' request for counting unique userIds and also
 * handle 'Put' request for updating Post at given index
 */

package com.flowers.codechallenge.controller;

import com.flowers.codechallenge.model.Post;
import com.flowers.codechallenge.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;


    @GetMapping(value = "/uniqueUserCount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getUniqueUserCount() {
        Long uniqueUserCount = postService.getUniqueUserCount();

        return new ResponseEntity<>(uniqueUserCount, HttpStatus.OK);
    }


    @PutMapping(value = "/{index}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getUpdatedPost(@PathVariable("index") Long index, @Valid @RequestBody Post post) {
        Post updatedPost = postService.getUpdatedPost(index, post);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

}
