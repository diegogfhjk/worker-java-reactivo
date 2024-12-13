package com.dagh.model.product.gateways;

import com.dagh.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> getProduct(String id);
}
