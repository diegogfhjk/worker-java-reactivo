package com.dagh.graphqlconsumer.adaptor;

import com.dagh.graphqlconsumer.GraphQLClient;
import com.dagh.model.client.Client;
import com.dagh.model.client.gateways.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final GraphQLClient graphQLClient;

    @Override
    public Mono<Client> getCustomer(String id) {
        String query = """
            query($id: String!) {
                getCustomerById(id: $id) {
                    id
                    name
                    email
                    phone
                    address
                }
            }
        """;

        Map<String, Object> variables = Map.of("id", id);

        return graphQLClient.sendQuery(query,variables);
    }
}
