package com.modsen.promocodeservice.mapper;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.model.PromoCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromoCodeMapper {

    private final ModelMapper modelMapper;

    public PromoCodeResponse fromEntityToResponse(PromoCode promoCode) {
        return modelMapper.map(promoCode, PromoCodeResponse.class);
    }

    public PromoCode fromRequestToEntity(PromoCodeRequest promoCode) {
        return modelMapper.map(promoCode, PromoCode.class);
    }
}
