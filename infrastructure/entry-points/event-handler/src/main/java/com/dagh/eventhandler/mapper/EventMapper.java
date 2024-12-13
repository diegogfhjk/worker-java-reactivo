package com.dagh.eventhandler.mapper;

import com.dagh.model.order.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class EventMapper {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Mono<Order> mapToOrder(String event) {
        try {
            return Mono.just(objectMapper.readValue(event, Order.class));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }


}
