/**
 * This Test class has several testcases to check whether
 * the business logic is working as expected
 *
 * @author Nagendra Kumar Aluru
 */

package com.flowers.codechallenge.service;

import com.flowers.codechallenge.model.Post;
import com.flowers.codechallenge.service.impl.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;

   /* @Mock
    private WebClient.RequestBodySpec requestBodyMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;*/

    @Mock
    private WebClient.ResponseSpec responseMock;


    @Test
    @DisplayName(value = "Test case to check all posts are returned or not")
    void should_ReturnAllPosts_When_getPostsCalls() {
        //given
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post(1, 1, "1800Flowers1", "1800Flowers1"));
        expectedPosts.add(new Post(1, 2, "1800Flowers2", "1800Flowers2"));
        expectedPosts.add(new Post(2, 3, "1800Flowers3", "1800Flowers3"));
        expectedPosts.add(new Post(3, 4, "1800Flowers4", "1800Flowers4"));

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/posts")).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<Post>>() {})).thenReturn(Mono.just(expectedPosts));

        //when
        List<Post> actualPosts = postService.getPosts();

        //then
        assertAll(
                () -> assertEquals(expectedPosts.get(1).getUserId(), actualPosts.get(1).getUserId()),
                () -> assertEquals(expectedPosts.get(1).getId(), actualPosts.get(1).getId()),
                () -> assertEquals(expectedPosts.get(1).getTitle(), actualPosts.get(1).getTitle()),
                () -> assertEquals(expectedPosts.get(1).getBody(), actualPosts.get(1).getBody())
        );

    }

    @Test
    @DisplayName(value = "Test case to check whether the exception throws or not")
    void should_ThrowException_When_PostServiceIsUnavailable() {
        //given
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post(1, 1, "1800Flowers1", "1800Flowers1"));
        expectedPosts.add(new Post(1, 2, "1800Flowers2", "1800Flowers2"));
        expectedPosts.add(new Post(2, 3, "1800Flowers3", "1800Flowers3"));
        expectedPosts.add(new Post(3, 4, "1800Flowers4", "1800Flowers4"));

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/posts")).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenThrow(WebClientResponseException.class);

        //when
        Executable executable = () -> postService.getPosts();

        //then
        assertThrows(WebClientResponseException.class, executable);

    }

    @Test
    @DisplayName(value = "Test case to check the unique userIds")
    void should_ReturnUniqueUserCount_When_getUniqueUserCountCalls() {
        //given
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post(1, 1, "1800Flowers1", "1800Flowers1"));
        expectedPosts.add(new Post(1, 2, "1800Flowers2", "1800Flowers2"));
        expectedPosts.add(new Post(2, 3, "1800Flowers3", "1800Flowers3"));
        expectedPosts.add(new Post(3, 4, "1800Flowers4", "1800Flowers4"));
        expectedPosts.add(new Post(3, 5, "1800Flowers3", "1800Flowers3"));
        expectedPosts.add(new Post(3, 6, "1800Flowers4", "1800Flowers4"));
        expectedPosts.add(new Post(4, 7, "1800Flowers3", "1800Flowers3"));
        expectedPosts.add(new Post(5, 8, "1800Flowers4", "1800Flowers4"));

        Long expectedUniqueUserCount = 5l;

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/posts")).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<Post>>() {})).thenReturn(Mono.just(expectedPosts));

        //when
        Long actualUniqueUserCount = postService.getUniqueUserCount();

        //then
        assertEquals(expectedUniqueUserCount, actualUniqueUserCount);

    }

    @Test
    @DisplayName(value = "Test case to check given indexed Post is updated or not")
    void should_ReturnUpdatedPostsList_When_getUpdatedPostsCalls() {
        //given
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post(1, 1, "1800Flowers1", "1800Flowers1"));
        expectedPosts.add(new Post(1, 2, "1800Flowers2", "1800Flowers2"));
        expectedPosts.add(new Post(2, 3, "1800Flowers3", "1800Flowers3"));
        expectedPosts.add(new Post(3, 4, "1800Flowers4", "1800Flowers4"));
        expectedPosts.add(new Post(3, 5, "1800Flowers5", "1800Flowers5"));
        expectedPosts.add(new Post(3, 6, "1800Flowers6", "1800Flowers6"));
        expectedPosts.add(new Post(4, 7, "1800Flowers7", "1800Flowers7"));
        expectedPosts.add(new Post(5, 8, "1800Flowers8", "1800Flowers8"));

        Post expectedUpdatedPost = new Post(3, 4, "1800Flowers", "1800Flowers");

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("/posts")).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<Post>>() {})).thenReturn(Mono.just(expectedPosts));

        //when
        Post actualUpdatedPost = postService.getUpdatedPost(4l, expectedUpdatedPost);

        //then
        assertAll(
                () -> assertEquals(expectedUpdatedPost.getTitle(), actualUpdatedPost.getTitle()),
                () -> assertEquals(expectedUpdatedPost.getBody(), actualUpdatedPost.getBody())
        );

    }
}