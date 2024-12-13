package com.dagh.mongo.model;


import com.dagh.model.client.Client;
import com.dagh.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class EnrichedOrderEntity {

    @Id
    private String id;
    private Client client;
    private List<Product> products;
}
