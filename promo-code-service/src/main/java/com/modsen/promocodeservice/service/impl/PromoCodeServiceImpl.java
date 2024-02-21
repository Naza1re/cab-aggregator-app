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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
                .orElseThrow(() -> new PromoCodeNotFoundException(String.format(ExceptionMessages.PROMOCODE_NOT_FOUND, id)));
    }

    @Override
    public PromoCodeResponse getPromoCodeById(Long id) {
        PromoCode promoCode = getOrThrow(id);
        return promoCodeMapper.fromEntityToResponse(promoCode);
    }

    @Override
    public PromoCodeResponse createPromoCode(PromoCodeRequest request) {

        checkPromoCodeExist(request.getValue());

        PromoCode promoCode = promoCodeMapper.fromRequestToEntity(request);
        PromoCode savedPromo = promoCodeRepository.save(promoCode);

        return promoCodeMapper.fromEntityToResponse(savedPromo);
    }

    @Override
    public PromoCodeResponse updatePromoCode(Long id, PromoCodeRequest request) {
        checkPromoCodeExist(request.getValue());
        PromoCode promoCode = getOrThrow(id);

        promoCode = promoCodeMapper.fromRequestToEntity(request);
        PromoCode savedPromo = promoCodeRepository.save(promoCode);

        return promoCodeMapper.fromEntityToResponse(savedPromo);
    }

    @Override
    public PromoCodeResponse deletePromoCodeById(Long id) {
        PromoCode promoCode = getOrThrow(id);

        promoCodeRepository.delete(promoCode);
        return promoCodeMapper.fromEntityToResponse(promoCode);
    }

    private void checkPromoCodeExist(String value) {
        if (promoCodeRepository.existsByValue(value)) {
            throw new PromoCodeAllReadyExistException(String.format(ExceptionMessages.PROMOCODE_ALREADY_EXIST, value));
        }
    }


}
