package com.dagh.graphqlconsumer;

import com.dagh.graphqlconsumer.model.GetCustomerResponse;
import com.dagh.graphqlconsumer.model.GraphQLResponse;
import com.dagh.model.client.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GraphQLClient {

    private final WebClient webClient;

    public GraphQLClient(@Qualifier("GraphQLWebClient") WebClient graphQLWebClient) {
        this.webClient = graphQLWebClient;
    }

    public Mono<Client> sendQuery(String query, Map<String, Object> variables) {
        Map<String, Object> requestPayload = Map.of(
            "query", query,
            "variables", variables
        );

        return webClient.post()
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<GraphQLResponse<GetCustomerResponse>>() {})
                .map(clientGraphQLResponse -> clientGraphQLResponse.getData().getGetCustomerById());
    }
}