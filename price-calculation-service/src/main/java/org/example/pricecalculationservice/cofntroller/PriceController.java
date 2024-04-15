package org.example.pricecalculationservice.cofntroller;

import lombok.RequiredArgsConstructor;
import org.example.pricecalculationservice.service.PriceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/price")
public class PriceController {

    private final PriceService priceService;
}
