package com.prosports.service;

import com.prosports.dto.OnlyPriceResponseDto;
import com.prosports.dto.PriceRequestDto;
import com.prosports.dto.PriceResponseDto;

public interface PriceService {

	OnlyPriceResponseDto findPriceBySku(String sku);

	PriceResponseDto addPrice(PriceRequestDto priceRequestDto);

}