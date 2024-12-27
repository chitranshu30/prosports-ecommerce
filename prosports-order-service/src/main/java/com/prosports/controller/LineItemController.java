package com.prosports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dto.LineItemRequest;
import com.prosports.dto.LineItemResponse;
import com.prosports.services.LineItemService;

@RestController
@RequestMapping("/lineItem")
public class LineItemController {

	@Autowired
	private LineItemService lineItemService;

	// Add a line item to the cart.
	@PostMapping("/add")
	public ResponseEntity<LineItemResponse> addLineItemToCart(@RequestBody LineItemRequest lineItemRequest) {
		LineItemResponse response = lineItemService.addLineItemToCart(lineItemRequest);
		return new ResponseEntity<LineItemResponse>(response, HttpStatus.OK);
	}

	// Remove a line item from the cart.
	@DeleteMapping("/remove")
	public void removeLineItem(@RequestBody LineItemRequest lineItemRequest) {
		lineItemService.removeLineItemFromCart(lineItemRequest);
	}

}
