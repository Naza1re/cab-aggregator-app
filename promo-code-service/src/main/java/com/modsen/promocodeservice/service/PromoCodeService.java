package com.modsen.promocodeservice.service;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponseList;

public interface PromoCodeService {


    PromoCodeResponseList getAllPromoCodes();

    PromoCodeResponse getPromoCodeById(Long id);

    PromoCodeResponse createPromoCode(PromoCodeRequest request);

    PromoCodeResponse updatePromoCode(Long id, PromoCodeRequest request);

    PromoCodeResponse deletePromoCodeById(Long id);
}
