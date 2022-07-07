/**
 * This class has a method to create a WebClient instance
 * with given base URL, sets the Http Header details and
 * returns the instance.
 *
 * @author Nagendra Kumar Aluru
 */

package com.flowers.codechallenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    @Value("${postsServiceBaseUrl}")
    private String postsServiceBaseUrl;


    /**
     * This bean creates WebClient instance with given base URL
     * with Http header details and returns the instance
     *
     * @return webClient
     */
    @Bean
    public WebClient httpClient() {
        LOGGER.info("Preparing httpClient with baseUrl: {}", postsServiceBaseUrl);
        String postsBaseUrl = postsServiceBaseUrl;
        return WebClient.builder().baseUrl(postsBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
