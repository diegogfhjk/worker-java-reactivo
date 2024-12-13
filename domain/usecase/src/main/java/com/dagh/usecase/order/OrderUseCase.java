package com.dagh.usecase.order;

import com.dagh.model.client.Client;
import com.dagh.model.client.gateways.ClientRepository;
import com.dagh.model.order.EnrichedOrder;
import com.dagh.model.order.Order;
import com.dagh.model.order.gateways.OrderRepository;
import com.dagh.model.product.Product;
import com.dagh.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class OrderUseCase {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public Mono<Boolean> receiveOrder(Order order) {

        Mono<Client> customerMono = clientRepository.getCustomer(order.getClientId());

        Mono<List<Product>> productsMono = Mono.just(order.getProductosIds())
                .flatMapMany(Flux::fromIterable)
                .flatMap(productRepository::getProduct)
                .collectList();

        Mono<EnrichedOrder> enrichedOrderMono = Mono.zip(customerMono, productsMono)
                .map(tuple -> {
                    Client client = tuple.getT1();
                    List<Product> products = tuple.getT2();

                    return EnrichedOrder.builder()
                            .id(order.getId())
                            .client(client)
                            .products(products)
                            .build();
                });

        return enrichedOrderMono
                .flatMap(orderRepository::saveOrder)
                .doOnNext(savedOrder -> System.out.println("Order saved: " + savedOrder))
                .map(savedOrder -> true);
    }
}
