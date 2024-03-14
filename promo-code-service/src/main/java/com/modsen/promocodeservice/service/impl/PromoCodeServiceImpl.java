package com.modsen.promocodeservice.service.impl;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponseList;
import com.modsen.promocodeservice.exceptions.PromoCodeAllReadyExistException;
import com.modsen.promocodeservice.exceptions.PromoCodeNotFoundException;
import com.modsen.promocodeservice.mapper.PromoCodeMapper;
import com.modsen.promocodeservice.model.PromoCode;
import com.modsen.promocodeservice.repository.PromoCodeRepository;
import com.modsen.promocodeservice.service.PromoCodeService;
import com.modsen.promocodeservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.modsen.promocodeservice.util.ConstantsMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public PromoCodeResponseList getAllPromoCodes() {
        List<PromoCode> promoCodeList = promoCodeRepository.findAll();
        List<PromoCodeResponse> promoCodeResponseList = promoCodeList
                .stream()
                .map(promoCodeMapper::fromEntityToResponse)
                .toList();
        return new PromoCodeResponseList(promoCodeResponseList);
    }

    private PromoCode getOrThrow(Long id) {
        return promoCodeRepository.findById(id)
                .orElseThrow(() -> new PromoCodeNotFoundException(String.format(ExceptionMessages.PROMOCODE_NOT_FOUND_BY_ID, id)));
    }

    @Override
    public PromoCodeResponse getPromoCodeById(Long id) {
        PromoCode promoCode = getOrThrow(id);
        log.info(String.format(GET_PROMO_CODE_BY_ID, id));
        return promoCodeMapper.fromEntityToResponse(promoCode);
    }

    @Override
    public PromoCodeResponse createPromoCode(PromoCodeRequest request) {

        checkPromoCodeExist(request.getValue());

        PromoCode promoCode = promoCodeMapper.fromRequestToEntity(request);
        PromoCode savedPromo = promoCodeRepository.save(promoCode);

        log.info(String.format(CREATE_PROMO_CODE, savedPromo.getId()));
        return promoCodeMapper.fromEntityToResponse(savedPromo);
    }

    @Override
    public PromoCodeResponse updatePromoCode(Long id, PromoCodeRequest request) {
        checkPromoCodeExist(request.getValue());
        PromoCode promoCode = getOrThrow(id);

        promoCode = promoCodeMapper.fromRequestToEntity(request);
        promoCode.setId(id);
        PromoCode savedPromo = promoCodeRepository.save(promoCode);

        log.info(String.format(UPDATE_PROMO_CODE, id));
        return promoCodeMapper.fromEntityToResponse(savedPromo);
    }

    @Override
    public PromoCodeResponse deletePromoCodeById(Long id) {
        PromoCode promoCode = getOrThrow(id);

        promoCodeRepository.delete(promoCode);
        log.info(String.format(DELETE_PROMO_CODE, id));
        return promoCodeMapper.fromEntityToResponse(promoCode);
    }

    @Override
    public PromoCodeResponse getPromoCodeByValue(String value) {
        Optional<PromoCode> promoCode = promoCodeRepository.findByValue(value);
        if (promoCode.isPresent()) {
            return promoCodeMapper.fromEntityToResponse(promoCode.get());
        } else {
            throw new PromoCodeNotFoundException(String.format(ExceptionMessages.PROMOCODE_NOT_FOUND_BY_VALUE, value));
        }
    }

    private void checkPromoCodeExist(String value) {
        if (promoCodeRepository.existsByValue(value)) {
            log.info(String.format(PROMO_CODE_EXIST, value));
            throw new PromoCodeAllReadyExistException(String.format(ExceptionMessages.PROMOCODE_ALREADY_EXIST, value));
        }
    }

}
