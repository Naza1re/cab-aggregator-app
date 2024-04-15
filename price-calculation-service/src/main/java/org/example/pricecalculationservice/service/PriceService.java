package org.example.pricecalculationservice.service;


import lombok.RequiredArgsConstructor;
import org.example.pricecalculationservice.repository.PriceRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PriceService {
    private final PriceRepository priceRepository;
}
