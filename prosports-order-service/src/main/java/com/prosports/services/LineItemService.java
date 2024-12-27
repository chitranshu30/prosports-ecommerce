package com.prosports.services;

import com.prosports.dto.LineItemRequest;
import com.prosports.dto.LineItemResponse;
import com.prosports.model.Cart;

public interface LineItemService {

	// Add a line item to the cart.
	LineItemResponse addLineItemToCart(LineItemRequest lineItemRequest);

	void removeLineItemFromCart(LineItemRequest lineItemRequest);

	Cart validateCartState(String cartKey);

}