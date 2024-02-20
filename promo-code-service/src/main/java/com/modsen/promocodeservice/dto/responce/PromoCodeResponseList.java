package com.modsen.promocodeservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PromoCodeResponseList {
    private List<PromoCodeResponse> promoCodeResponseList;
}
