package com.prosports.services;

import com.prosports.dto.OrderRequest;
import com.prosports.dto.OrderResponse;

public interface OrderService {

	// Create an order from an active cart.
	OrderResponse createOrderFromCart(OrderRequest orderRequest);

	OrderResponse getPaymentStateFromPaymentService(String orderKey);

}