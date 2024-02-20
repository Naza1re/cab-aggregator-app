package com.modsen.promocodeservice.dto.responce;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoCodeResponse {

    private Long id;
    private String name;
    private String value;
    private int percent;
}
