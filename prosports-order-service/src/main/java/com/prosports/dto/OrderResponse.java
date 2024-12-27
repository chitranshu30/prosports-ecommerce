package com.prosports.dto;

import com.prosports.model.Cart;
import com.prosports.model.PaymentState;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {

	private String orderKey;
	private PaymentState paymentState;
	private Cart cart;

}
