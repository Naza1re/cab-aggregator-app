package com.modsen.promocodeservice.util;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponseList;
import com.modsen.promocodeservice.model.PromoCode;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PromoCodeUtilTest {

    public final String DEFAULT_ID = "1";
    public final String NOT_FOUND_ID = "2";
    public final String DEFAULT_NAME = "STUDENT";
    public final String DEFAULT_VALUE = "1222";
    public final int DEFAULT_PERCENT = 20;

    public final String SECOND_ID = "3";
    public final String SECOND_NAME = "DRIVERS";
    public final String SECOND_VALUE = "4645";
    public final int SECOND_PERCENT = 20;

    public PromoCodeRequest getPromoCodeRequestForUpdate() {
        return PromoCodeRequest.builder()
                .name(SECOND_NAME)
                .value(SECOND_VALUE)
                .percent(SECOND_PERCENT)
                .build();
    }

    public PromoCode getUpdatedPromoCode() {
        return PromoCode.builder()
                .id(DEFAULT_ID)
                .name(SECOND_NAME)
                .value(SECOND_VALUE)
                .percent(SECOND_PERCENT)
                .build();
    }

    public PromoCodeResponse getUpdatedPromoCodeResponse() {
        return PromoCodeResponse.builder()
                .id(DEFAULT_ID)
                .name(SECOND_NAME)
                .value(SECOND_VALUE)
                .percent(SECOND_PERCENT)
                .build();
    }


    public List<PromoCode> getAllPromoCodeList() {
        return List.of(getDefaultSavedPromoCode(), getSecondPromoCode());
    }

    public PromoCodeResponseList getDefaultPromoCodeResponseList() {
        return PromoCodeResponseList.builder()
                .promoCodeResponseList(List.of(getDefaultPromoCodeResponse(), getSecondPromoCodeResponse()))
                .build();
    }

    public PromoCode getUpdatePromoCodeNotSaved() {
        return PromoCode.builder()
                .name(SECOND_NAME)
                .value(SECOND_VALUE)
                .percent(SECOND_PERCENT)
                .build();
    }

    public PromoCode getSecondPromoCode() {
        return PromoCode.builder()
                .name(SECOND_NAME)
                .value(SECOND_VALUE)
                .percent(SECOND_PERCENT)
                .id(SECOND_ID)
                .build();
    }

    public PromoCodeResponse getSecondPromoCodeResponse() {
        return PromoCodeResponse.builder()
                .id(SECOND_ID)
                .name(SECOND_NAME)
                .value(DEFAULT_VALUE)
                .percent(SECOND_PERCENT)
                .build();
    }

    public static PromoCodeResponse getDefaultPromoCodeResponse() {
        return PromoCodeResponse.builder()
                .name(DEFAULT_NAME)
                .percent(DEFAULT_PERCENT)
                .value(DEFAULT_VALUE)
                .id(DEFAULT_ID)
                .build();
    }

    public static PromoCode getDefaultSavedPromoCode() {
        return PromoCode.builder()
                .name(DEFAULT_NAME)
                .value(DEFAULT_VALUE)
                .percent(DEFAULT_PERCENT)
                .id(DEFAULT_ID)
                .build();
    }

    public static PromoCode getDefaultNotPromoCode() {
        return PromoCode.builder()
                .name(DEFAULT_NAME)
                .percent(DEFAULT_PERCENT)
                .percent(DEFAULT_PERCENT)
                .build();
    }

    public static PromoCodeRequest getDefaultPromoCodeRequest() {
        return PromoCodeRequest.builder()
                .name(DEFAULT_NAME)
                .percent(DEFAULT_PERCENT)
                .value(DEFAULT_VALUE)
                .build();
    }

    public static PromoCodeRequest getPromoCodeRequestWithValue(String value) {
        return PromoCodeRequest.builder()
                .name(DEFAULT_NAME)
                .percent(DEFAULT_PERCENT)
                .value(value)
                .build();
    }


}
