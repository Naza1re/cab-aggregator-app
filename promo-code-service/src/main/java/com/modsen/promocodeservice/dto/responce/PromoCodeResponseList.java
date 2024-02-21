package com.modsen.promocodeservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class PromoCodeResponseList {
    private List<PromoCodeResponse> promoCodeResponseList;
}
