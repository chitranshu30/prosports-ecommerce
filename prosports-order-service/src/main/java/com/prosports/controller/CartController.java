package com.prosports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dto.CartResponse;
import com.prosports.dto.ProductVariantResponseDto;
import com.prosports.services.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public ResponseEntity<CartResponse> createCart() {
		CartResponse response = cartService.createNewCart();
		return new ResponseEntity<CartResponse>(response, HttpStatus.CREATED);
	}

	@GetMapping("/getCart/{cartKey}")
	public ResponseEntity<CartResponse> findCartByKey(@PathVariable String cartKey) {
		CartResponse response = cartService.findCartByKey(cartKey);
		return new ResponseEntity<CartResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getRecommendations/{lineItemSku}")
	public ResponseEntity<List<ProductVariantResponseDto>> getRecommendation(@Valid @PathVariable String lineItemSku) {
		return new ResponseEntity<List<ProductVariantResponseDto>>(cartService.getRecommendation(lineItemSku),
				HttpStatus.OK);
	}

}
