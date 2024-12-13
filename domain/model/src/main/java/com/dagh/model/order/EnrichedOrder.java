package com.dagh.model.order;


import com.dagh.model.client.Client;
import com.dagh.model.product.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class EnrichedOrder {

    public String id;
    public Client client;
    public List<Product> products;

}
