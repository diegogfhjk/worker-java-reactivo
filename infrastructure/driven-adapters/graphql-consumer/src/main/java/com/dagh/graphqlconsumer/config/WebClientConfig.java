package com.dagh.graphqlconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final String url;

    public WebClientConfig(@Value("${adapter.grapql.url}") String url) {
        this.url = url;
    }

    @Bean(name = "GraphQLWebClient")
    public WebClient graphQLWebClient() {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}