package com.modsen.promocodeservice.service;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponseList;

public interface PromoCodeService {


    PromoCodeResponseList getAllPromoCodes();

    PromoCodeResponse getPromoCodeById(String id);

    PromoCodeResponse createPromoCode(PromoCodeRequest request);

    PromoCodeResponse updatePromoCode(String id, PromoCodeRequest request);

    PromoCodeResponse deletePromoCodeById(String id);

    PromoCodeResponse getPromoCodeByValue(String value);
}
