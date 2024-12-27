package com.prosports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dto.OnlyPriceResponseDto;
import com.prosports.dto.PriceRequestDto;
import com.prosports.dto.PriceResponseDto;
import com.prosports.service.PriceService;

@RestController
@RequestMapping("/price")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@PostMapping("/add")
	public ResponseEntity<PriceResponseDto> addPrice(@RequestBody PriceRequestDto priceRequestDto) {
		return new ResponseEntity<PriceResponseDto>(priceService.addPrice(priceRequestDto), HttpStatus.CREATED);
	}

	@GetMapping("/findPriceBySku/{productVariantSku}")
	public ResponseEntity<OnlyPriceResponseDto> findPriceBySku(@PathVariable("productVariantSku") String productVariantSku) {
		return new ResponseEntity<OnlyPriceResponseDto>(priceService.findPriceBySku(productVariantSku),HttpStatus.OK);
	}

	
}
