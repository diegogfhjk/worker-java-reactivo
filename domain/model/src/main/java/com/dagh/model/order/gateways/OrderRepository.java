package com.dagh.model.order.gateways;

import com.dagh.model.order.EnrichedOrder;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<EnrichedOrder> saveOrder(EnrichedOrder order);
}
