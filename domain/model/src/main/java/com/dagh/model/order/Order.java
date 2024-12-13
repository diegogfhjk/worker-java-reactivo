package com.dagh.model.order;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Order {

    private String id;
    private List<String> productosIds;
    private String clientId;

}
