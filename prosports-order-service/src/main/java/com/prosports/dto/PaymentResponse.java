package com.prosports.dto;

import java.math.BigDecimal;

import com.prosports.model.PaymentState;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {

	private String key;
	private BigDecimal amount;
	private PaymentState paymentStatus;

}
