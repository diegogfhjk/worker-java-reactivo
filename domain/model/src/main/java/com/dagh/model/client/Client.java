package com.dagh.model.client;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Client {

        private String id;
        private String name;
        private String email;
        private String phone;
        private String address;

}
