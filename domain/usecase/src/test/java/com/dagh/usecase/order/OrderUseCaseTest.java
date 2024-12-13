package com.dagh.usecase.order;

import com.dagh.model.client.Client;
import com.dagh.model.client.gateways.ClientRepository;
import com.dagh.model.order.EnrichedOrder;
import com.dagh.model.order.Order;
import com.dagh.model.order.gateways.OrderRepository;
import com.dagh.model.product.Product;
import com.dagh.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveOrder() {
        Order order = Order.builder()
                .id("order123")
                .clientId("client1")
                .productosIds(List.of("product1", "product2"))
                .build();

        Client mockClient = new Client("client1", "Test Client", "test@example.com", "123456789", "Test Address");
        Product mockProduct1 = new Product("product1", "Product 1", "Description 1", 10.0);
        Product mockProduct2 = new Product("product2", "Product 2", "Description 2", 20.0);

        EnrichedOrder mockEnrichedOrder = EnrichedOrder.builder()
                .id("order123")
                .client(mockClient)
                .products(List.of(mockProduct1, mockProduct2))
                .build();

        when(clientRepository.getCustomer("client1")).thenReturn(Mono.just(mockClient));
        when(productRepository.getProduct("product1")).thenReturn(Mono.just(mockProduct1));
        when(productRepository.getProduct("product2")).thenReturn(Mono.just(mockProduct2));
        when(orderRepository.saveOrder(any(EnrichedOrder.class))).thenReturn(Mono.just(mockEnrichedOrder));

        Mono<Boolean> result = orderUseCase.receiveOrder(order);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(clientRepository).getCustomer("client1");
        verify(productRepository).getProduct("product1");
        verify(productRepository).getProduct("product2");
        verify(orderRepository).saveOrder(any(EnrichedOrder.class));
    }
}