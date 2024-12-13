package com.dagh.graphqlconsumer.model;

import lombok.Data;

@Data
public class GraphQLResponse<T> {
    private T data;
}