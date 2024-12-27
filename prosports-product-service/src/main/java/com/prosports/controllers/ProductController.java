package com.prosports.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dtos.ProductRequestDto;
import com.prosports.dtos.ProductResponseDto;
import com.prosports.dtos.ProductVariantResponseDto;
import com.prosports.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/findByName/{productName}")
	public ResponseEntity<ProductResponseDto> findProductByName(@Valid @PathVariable String productName) {
		return new ResponseEntity<ProductResponseDto>(productService.findProductByName(productName), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
		return new ResponseEntity<ProductResponseDto>(productService.createProduct(productRequestDto),
				HttpStatus.CREATED);
	}

	@GetMapping("/findProductVariantBySku/{productVariantSku}")
	public ResponseEntity<ProductVariantResponseDto> findProductVariantBySku(
			@Valid @PathVariable String productVariantSku) {
		return new ResponseEntity<ProductVariantResponseDto>(productService.findProductVariant(productVariantSku),
				HttpStatus.OK);
	}

	@GetMapping("/getRecommendations/{productVariantSku}")
	public ResponseEntity<List<ProductVariantResponseDto>> getRecommendation(
			@Valid @PathVariable String productVariantSku) {
		return new ResponseEntity<List<ProductVariantResponseDto>>(productService.recommendation(productVariantSku),
				HttpStatus.OK);
	}

}
