package com.prosports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dto.OrderRequest;
import com.prosports.dto.OrderResponse;
import com.prosports.dto.PaymentRequest;
import com.prosports.dto.PaymentResponse;
import com.prosports.openFeign.PaymentServiceopenFeign;
import com.prosports.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PaymentServiceopenFeign paymentServiceOpenFeign;

//	Create an order from a cart.
	@PostMapping("/create")
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
		return new ResponseEntity<OrderResponse>(orderService.createOrderFromCart(orderRequest), HttpStatus.CREATED);
	}

	@PostMapping("/makePayment")
	public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {
		System.out.println("PaymentRequest: " + paymentRequest.getKey());
		return new ResponseEntity<PaymentResponse>(paymentServiceOpenFeign.processPayment(paymentRequest.getKey(),paymentRequest.getAmount()),
				HttpStatus.OK);
	}

//	 Update the payment state of an order.
	@PatchMapping("/updatePaymentState/{orderKey}")
	public ResponseEntity<OrderResponse> getPaymentState(@PathVariable String orderKey) {
		return new ResponseEntity<OrderResponse>(orderService.getPaymentStateFromPaymentService(orderKey),
				HttpStatus.OK);
	}
}
