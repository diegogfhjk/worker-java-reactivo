package com.dagh.mongo;

import com.dagh.mongo.model.EnrichedOrderEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface MongoDBRepository extends ReactiveMongoRepository<EnrichedOrderEntity, String>, ReactiveQueryByExampleExecutor<EnrichedOrderEntity> {
}
