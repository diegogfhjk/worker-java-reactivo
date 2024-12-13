package com.dagh.model.product;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Product {
    private String id;
    private String name;
    private String description;
    private Double price;
}
