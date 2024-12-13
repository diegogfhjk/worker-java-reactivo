package com.dagh.eventhandler.Handler;

import com.dagh.eventhandler.mapper.EventMapper;
import com.dagh.usecase.order.OrderUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EventsHandler {

    private final EventMapper mapper;
    private final OrderUseCase orderUseCase;

    public Mono<Void> handleEvent(String event) {
        return Mono.just(event)
                        .flatMap(mapper::mapToOrder)
                        .flatMap(orderUseCase::receiveOrder)
                        .onErrorResume(e -> Mono.empty())
                         .then();
    }
}
