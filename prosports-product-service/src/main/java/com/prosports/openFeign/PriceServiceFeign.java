package com.prosports.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prosports.dtos.OnlyPriceResponseDto;

@FeignClient(name = "prosports-price-service")
public interface PriceServiceFeign {

	@GetMapping("/price/findPriceBySku/{sku}")
	public OnlyPriceResponseDto findPriceBySku(@PathVariable("sku") String sku);

}
