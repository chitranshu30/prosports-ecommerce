package com.prosports.openFeign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prosports.dto.PaymentResponse;

@FeignClient(name = "prosports-payment-service")
public interface PaymentServiceopenFeign {

	@PostMapping("/payments/processPayment")
	public PaymentResponse processPayment(@RequestParam String key, @RequestParam BigDecimal amount);

	@GetMapping("/payments/getPaymentStatus/{orderKey}")
	public PaymentResponse getPaymentStatus(@PathVariable String orderKey);

}
