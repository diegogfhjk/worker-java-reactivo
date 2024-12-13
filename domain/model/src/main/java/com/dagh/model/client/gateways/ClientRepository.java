package com.dagh.model.client.gateways;

import com.dagh.model.client.Client;
import reactor.core.publisher.Mono;

public interface ClientRepository {
    Mono<Client> getCustomer(String id);
}
