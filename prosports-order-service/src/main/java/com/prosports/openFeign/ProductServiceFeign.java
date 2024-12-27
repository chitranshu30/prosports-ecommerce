package com.prosports.openFeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prosports.dto.ProductVariantResponseDto;

import jakarta.validation.Valid;

@FeignClient(name = "prosports-product-service")
public interface ProductServiceFeign {

	@GetMapping("/api/product/getRecommendations/{productVariantSku}")
	public List<ProductVariantResponseDto> getRecommendation(@Valid @PathVariable String productVariantSku);

}
