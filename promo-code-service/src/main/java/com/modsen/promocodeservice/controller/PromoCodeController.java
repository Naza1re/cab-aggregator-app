package com.modsen.promocodeservice.controller;

import com.modsen.promocodeservice.dto.request.PromoCodeRequest;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponse;
import com.modsen.promocodeservice.dto.responce.PromoCodeResponseList;
import com.modsen.promocodeservice.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promo-code")
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    @GetMapping
    public ResponseEntity<PromoCodeResponseList> getAllPromoCodes() {
        return ResponseEntity.ok()
                .body(promoCodeService.getAllPromoCodes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeResponse> getPromoCodeById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(promoCodeService.getPromoCodeById(id));
    }

    @PostMapping()
    public ResponseEntity<PromoCodeResponse> createPromoCode(@RequestBody PromoCodeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(promoCodeService.createPromoCode(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoCodeResponse> updatePromoCode(@RequestBody PromoCodeRequest request, @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(promoCodeService.updatePromoCode(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PromoCodeResponse> deletePromoCode(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(promoCodeService.deletePromoCodeById(id));
    }






}
