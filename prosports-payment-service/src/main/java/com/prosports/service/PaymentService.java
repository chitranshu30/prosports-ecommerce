package com.prosports.service;

import java.math.BigDecimal;

import com.prosports.dto.PaymentRequest;
import com.prosports.dto.PaymentResponse;

public interface PaymentService {

	PaymentResponse processPayment(String key, BigDecimal amount);

	public PaymentResponse getPaymentStatus(String orderKey);

}
