package com.prosports.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dto.PaymentResponse;
import com.prosports.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	// Endpoint to process a payment,
	// This endpoint is only called by order-service to execute the payment process
	@PostMapping("/processPayment")
	public ResponseEntity<PaymentResponse> processPayment(@RequestParam String key, @RequestParam BigDecimal amount) {
		return new ResponseEntity<PaymentResponse>(paymentService.processPayment(key, amount), HttpStatus.CREATED);
	} 

	// Endpoint to get the payment status by order key,
	// This endpoint is also used to update the order status and cart status in
	// order-service
	@GetMapping("/getPaymentStatus/{orderKey}")
	public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable String orderKey) {
		return new ResponseEntity<PaymentResponse>(paymentService.getPaymentStatus(orderKey), HttpStatus.OK);
	}

}