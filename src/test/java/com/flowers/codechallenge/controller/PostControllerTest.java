package com.flowers.codechallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowers.codechallenge.model.Post;
import com.flowers.codechallenge.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostServiceImpl postService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Post> posts;

    private Post post;

    @BeforeEach
    void setUp() {
        this.posts = new ArrayList<>();

        this.posts.add(new Post(1, 1, "1800Flowers1", "1800Flowers1"));
        this.posts.add(new Post(1, 2, "1800Flowers2", "1800Flowers2"));
        this.posts.add(new Post(2, 3, "1800Flowers3", "1800Flowers3"));
        this.posts.add(new Post(3, 4, "1800Flowers", "1800Flowers"));

        post = new Post(3, 4, "1800Flowers", "1800Flowers");
    }

    @Test
    void should_ReturnUniqueUserIdCount_When_GetRESTEndpoint_Calls() throws Exception {
        Long expectedUniqueUserCount = 5l;

        given(postService.getUniqueUserCount()).willReturn(expectedUniqueUserCount);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/posts/uniqueUserCount")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(5));

    }

    @Test
    void should_ReturnUpdatedPostsList_When_PutRESTEndpoint_Calls() throws Exception {
        Long index = 4L;
        this.posts.add(new Post(1, 1, "1800Flowers1", "1800Flowers1"));
        this.posts.add(new Post(1, 2, "1800Flowers2", "1800Flowers2"));
        this.posts.add(new Post(2, 3, "1800Flowers3", "1800Flowers3"));
        this.posts.add(new Post(3, 4, "1800Flowers", "1800Flowers"));
        this.posts.add(new Post(3, 5, "1800Flowers3", "1800Flowers3"));
        this.posts.add(new Post(3, 6, "1800Flowers4", "1800Flowers4"));
        this.posts.add(new Post(4, 7, "1800Flowers3", "1800Flowers3"));
        this.posts.add(new Post(5, 8, "1800Flowers4", "1800Flowers4"));

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");
        Post post = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers
                                .jsonPath("$.title").value("1800Flowers"))
                        .andExpect(MockMvcResultMatchers
                                .jsonPath("$.body").value("1800Flowers"));
    }


    @Test
    void should_ThrowMethodNotAllowedException() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");


        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(this.post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isMethodNotAllowed());

    }

    @Test
    void should_ThrowUnsupportedMediaTypeException() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(this.post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isUnsupportedMediaType());

    }

    @Test
    void should_ThrowBadRequest() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(this.post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest());

    }

    @Test
    void should_ThrowArrayIndexOutOfBoundsException() throws Exception {
        Long index = 400L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willThrow(ArrayIndexOutOfBoundsException.class);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest());

    }

    @Test
    void should_Throw404NotFound() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(this.post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts1/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNotFound());

    }

    @Test
    void should_ThrowJsonParserException() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(this.post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content("<xml><id></id></xml")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest());

    }

    @Test
    void should_ThrowWebClientResponseException() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willThrow(WebClientResponseException.class);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isServiceUnavailable());

    }

    @Test
    void should_ThrowMethodArgumentTypeMismatchException() throws Exception {
        Long index = 4L;
        String mismatchedType = "Test";

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willThrow(MethodArgumentTypeMismatchException.class);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", mismatchedType)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest());

    }

    @Test
    void should_ThrowHttpMediaTypeNotAcceptableException() throws Exception {
        Long index = 4L;

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        given(postService.getUpdatedPost(index, expectedUpdatedPost)).willReturn(this.post);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/posts/{index}", index)
                        .content(objectMapper.writeValueAsString(expectedUpdatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_PDF))
                        .andDo(print())
                        .andExpect(status().isNotAcceptable());

    }

}
