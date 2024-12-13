package com.dagh.graphqlconsumer.model;

import com.dagh.model.client.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCustomerResponse {
    private Client getCustomerById;

}