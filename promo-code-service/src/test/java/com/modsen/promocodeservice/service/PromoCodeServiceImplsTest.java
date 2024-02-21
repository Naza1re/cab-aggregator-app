package com.modsen.promocodeservice.service;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponseList;
import com.modsen.promocodeservice.exceptions.PromoCodeNotFoundException;
import com.modsen.promocodeservice.mapper.PromoCodeMapper;
import com.modsen.promocodeservice.model.PromoCode;
import com.modsen.promocodeservice.repository.PromoCodeRepository;
import com.modsen.promocodeservice.service.impl.PromoCodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.modsen.promocodeservice.util.PromoCodeUtilTest.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromoCodeServiceImplsTest {

    @Mock
    private PromoCodeRepository promoCodeRepository;
    @Mock
    private PromoCodeMapper promoCodeMapper;
    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;


    @Test
    void getPromoCodeById_whenPromoCodeExist() {
        PromoCodeResponse promoCodeResponse = getDefaultPromoCodeResponse();
        PromoCode promoCode = getDefaultSavedPromoCode();


        doReturn(Optional.of(promoCode))
                .when(promoCodeRepository)
                .findById(DEFAULT_ID);

        doReturn(promoCodeResponse)
                .when(promoCodeMapper)
                .fromEntityToResponse(promoCode);

        var actual = promoCodeService.getPromoCodeById(DEFAULT_ID);

        verify(promoCodeRepository).findById(DEFAULT_ID);
        verify(promoCodeMapper).fromEntityToResponse(promoCode);

        assertThat(actual).isEqualTo(promoCodeResponse);

    }


    @Test
    void getPromoCodeById_whenPromoCodeNotExist() {

        doReturn(Optional.empty())
                .when(promoCodeRepository)
                .findById(NOT_FOUND_ID);
        assertThrows(
                PromoCodeNotFoundException.class,
                () -> promoCodeService.getPromoCodeById(NOT_FOUND_ID)
        );
        verify(promoCodeRepository).findById(NOT_FOUND_ID);
    }

    @Test
    void createPromoCode_whenPromoCodeDataIsUnique() {
        PromoCodeRequest request = getDefaultPromoCodeRequest();
        PromoCodeResponse expected = getDefaultPromoCodeResponse();
        PromoCode savedPromoCode = getDefaultSavedPromoCode();
        PromoCode notSavedPromoCode = getDefaultNotPromoCode();


        doReturn(false)
                .when(promoCodeRepository)
                .existsByValue(DEFAULT_VALUE);
        doReturn(notSavedPromoCode)
                .when(promoCodeMapper)
                .fromRequestToEntity(request);
        doReturn(savedPromoCode)
                .when(promoCodeRepository)
                .save(notSavedPromoCode);
        doReturn(expected)
                .when(promoCodeMapper)
                .fromEntityToResponse(savedPromoCode);

        var actual = promoCodeService.createPromoCode(request);

        verify(promoCodeRepository).save(notSavedPromoCode);
        verify(promoCodeMapper).fromEntityToResponse(savedPromoCode);
        verify(promoCodeMapper).fromRequestToEntity(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllPromoCodes_shouldReturnPromoCodeListResponse() {
        PromoCodeResponseList promoCodeResponseList = getDefaultPromoCodeResponseList();
        List<PromoCode> promoCodeList = getAllPromoCodeList();

        doReturn(promoCodeList)
                .when(promoCodeRepository)
                .findAll();


        doReturn(promoCodeResponseList.getPromoCodeResponseList().get(0))
                .when(promoCodeMapper)
                .fromEntityToResponse(promoCodeList.get(0));
        doReturn(promoCodeResponseList.getPromoCodeResponseList().get(1))
                .when(promoCodeMapper)
                .fromEntityToResponse(promoCodeList.get(1));

        var actual = promoCodeService.getAllPromoCodes();

        verify(promoCodeRepository).findAll();
        verify(promoCodeMapper).fromEntityToResponse(promoCodeList.get(0));
        verify(promoCodeMapper).fromEntityToResponse(promoCodeList.get(1));

        assertThat(actual.getPromoCodeResponseList()).isEqualTo(promoCodeResponseList.getPromoCodeResponseList());
    }

    @Test
    void updatePromoCode_whenPromoCodeExist() {

        PromoCode promoCode = getDefaultSavedPromoCode();
        PromoCodeRequest promoCodeUpdateRequest = getPromoCodeRequestForUpdate();
        PromoCodeResponse expected = getUpdatedPromoCodeResponse();
        PromoCode updatePromoCode = getUpdatedPromoCode();

        doReturn(Optional.of(promoCode))
                .when(promoCodeRepository)
                .findById(DEFAULT_ID);
        doReturn(false)
                .when(promoCodeRepository)
                .existsByValue(promoCodeUpdateRequest.getValue());
        doReturn(updatePromoCode)
                .when(promoCodeMapper)
                .fromRequestToEntity(promoCodeUpdateRequest);
        doReturn(expected)
                .when(promoCodeMapper)
                .fromEntityToResponse(updatePromoCode);
        doReturn(updatePromoCode)
                .when(promoCodeRepository)
                .save(updatePromoCode);

        var actual = promoCodeService.updatePromoCode(DEFAULT_ID, promoCodeUpdateRequest);

        verify(promoCodeRepository).findById(DEFAULT_ID);
        verify(promoCodeRepository).existsByValue(promoCodeUpdateRequest.getValue());
        verify(promoCodeMapper).fromEntityToResponse(updatePromoCode);
        verify(promoCodeMapper).fromRequestToEntity(promoCodeUpdateRequest);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deletePromoCodeById_whenPromoCodeExist() {
        PromoCode promoCode = getDefaultSavedPromoCode();
        PromoCodeResponse promoCodeResponse = getDefaultPromoCodeResponse();


        doReturn(Optional.of(promoCode))
                .when(promoCodeRepository)
                .findById(DEFAULT_ID);
        doNothing()
                .when(promoCodeRepository)
                .delete(promoCode);
        doReturn(promoCodeResponse)
                .when(promoCodeMapper)
                .fromEntityToResponse(promoCode);


        promoCodeService.deletePromoCodeById(DEFAULT_ID);

        verify(promoCodeRepository).delete(promoCode);
        verify(promoCodeRepository).findById(DEFAULT_ID);

    }

}
