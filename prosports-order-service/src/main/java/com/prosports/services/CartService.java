package com.prosports.services;

import java.util.List;

import com.prosports.dto.CartResponse;
import com.prosports.dto.ProductVariantResponseDto;

public interface CartService {

	CartResponse createNewCart();

	CartResponse findCartByKey(String cartKey);

	List<ProductVariantResponseDto> getRecommendation(String lineItemSku);

}