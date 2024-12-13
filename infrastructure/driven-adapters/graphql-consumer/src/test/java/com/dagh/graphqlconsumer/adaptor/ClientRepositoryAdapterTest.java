package com.dagh.graphqlconsumer.adaptor;

import com.dagh.graphqlconsumer.GraphQLClient;
import com.dagh.model.client.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryAdapterTest {

    @Mock
    private GraphQLClient graphQLClient;

    @InjectMocks
    private ClientRepositoryAdapter clientRepositoryAdapter;

    @Test
    void testGetCustomer() {
        String clientId = "client1";
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

        Map<String, Object> variables = Map.of("id", clientId);

        Client mockClient = new Client("client1", "Test Client", "test@example.com", "123456789", "Test Address");

        when(graphQLClient.sendQuery(eq(query), eq(variables))).thenReturn(Mono.just(mockClient));

        Mono<Client> result = clientRepositoryAdapter.getCustomer(clientId);

        StepVerifier.create(result)
                .expectNext(mockClient)
                .verifyComplete();

        verify(graphQLClient).sendQuery(eq(query), eq(variables));
    }
}