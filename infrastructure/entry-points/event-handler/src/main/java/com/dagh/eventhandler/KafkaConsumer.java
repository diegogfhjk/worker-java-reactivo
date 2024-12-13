package com.dagh.eventhandler;
import com.dagh.eventhandler.Handler.EventsHandler;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    private final EventsHandler eventsHandler;

    @KafkaListener(topics = "pedidos", groupId = "grupo-pedidos")
    public void consume(String message) {
        eventsHandler.handleEvent(message).subscribe();
    }
}
