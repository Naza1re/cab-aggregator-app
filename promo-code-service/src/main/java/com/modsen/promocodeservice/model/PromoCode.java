package com.modsen.promocodeservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "promo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromoCode {
    @Id
    private String id;
    private String name;
    private String value;
    private double percent;


}
