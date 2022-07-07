package com.flowers.codechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private int userId;
    private int id;
    @NotBlank (message = "Property 'title' should not be null or blank !!")
    private String title;
    @NotBlank (message = "Property 'body' should not be null or blank !!")
    private String body;
}
