package com.dagh.mongo;

import com.dagh.model.order.EnrichedOrder;
import com.dagh.model.order.gateways.OrderRepository;
import com.dagh.mongo.helper.AdapterOperations;
import com.dagh.mongo.model.EnrichedOrderEntity;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<EnrichedOrder , EnrichedOrderEntity, String, MongoDBRepository> implements OrderRepository {

    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, EnrichedOrder.class));
    }

    @Override
    public Mono<EnrichedOrder> saveOrder(EnrichedOrder order) {
        System.out.println("Order received in adapter: " + order);
        return this.repository.save(mapper.map(order, EnrichedOrderEntity.class)).map(d -> mapper.map(d, EnrichedOrder.class));
    }
}
