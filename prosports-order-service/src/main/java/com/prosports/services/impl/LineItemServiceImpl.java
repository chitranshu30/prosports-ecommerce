package com.prosports.services.impl;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dto.LineItemRequest;
import com.prosports.dto.LineItemResponse;
import com.prosports.dto.OnlyPriceResponseDto;
import com.prosports.exceptions.CartModificationException;
import com.prosports.exceptions.CartNotFoundException;
import com.prosports.model.Cart;
import com.prosports.model.CartState;
import com.prosports.model.LineItem;
import com.prosports.openFeign.PriceServiceFeign;
import com.prosports.repositories.CartRepository;
import com.prosports.services.LineItemService;

@Service
public class LineItemServiceImpl implements LineItemService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PriceServiceFeign priceServiceFeign;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Adds a line item to the cart. Throws CartNotFoundException if the cart is not
	 * found.
	 *
	 * @param lineItemRequest the data transfer object containing the details of the
	 *                        line item to be added
	 * @return a LineItemResponse containing the details of the added line item
	 */
	@Override
	public LineItemResponse addLineItemToCart(LineItemRequest lineItemRequest) {
		String cartKey = lineItemRequest.getCart().getCartKey();
		Cart cart = cartRepository.findCartByCartKey(cartKey);
		if (cart != null) {
			validateCartState(cart.getCartKey());
			lineItemRequest.setCart(cart);
			LineItem lineItem = modelMapper.map(lineItemRequest, LineItem.class);
			OnlyPriceResponseDto priceResponseDto = priceServiceFeign.findPriceBySku(lineItem.getLineItemSku());
			lineItem.setLineItemPrice(priceResponseDto.getAmount());
			BigDecimal initialCartValue = BigDecimal.valueOf(00.00);
			if (cart.getTotalCartValue() != null) {
				initialCartValue = cart.getTotalCartValue();
			}
			BigDecimal currentCartValue = lineItem.getLineItemPrice()
					.multiply(BigDecimal.valueOf(lineItem.getLineItemQuantity()));
			BigDecimal updatedCartValue = initialCartValue.add(currentCartValue);
			cart.setTotalCartValue(updatedCartValue);
			cart.addLineItem(lineItem);
			cartRepository.save(cart);
			return modelMapper.map(lineItem, LineItemResponse.class);
		} else
			throw new CartNotFoundException("Cart not found with key " + cartKey);
	}

	/**
	 * Removes a line item from the cart. Throws CartNotFoundException if the cart
	 * is not found. Throws LineItemNotFoundException if the line item is not found.
	 *
	 * @param lineItemRequest the data transfer object containing the details of the
	 *                        line item to be removed
	 */
	@Override
	public void removeLineItemFromCart(LineItemRequest lineItemRequest) {
		Cart cart = cartRepository.findCartByCartKey(lineItemRequest.getCart().getCartKey());
		if (cart != null) {
			validateCartState(cart.getCartKey());
			LineItem lineItem = cart.getLineItems().stream()
					.filter(item -> item.getLineItemSku().equals(lineItemRequest.getLineItemSku())).findFirst()
					.orElseThrow(() -> new RuntimeException(
							"Line item with SKU " + lineItemRequest.getLineItemSku() + " not found"));
			;
			BigDecimal totalCost = lineItem.getLineItemPrice()
					.multiply(BigDecimal.valueOf(lineItem.getLineItemQuantity()));
			cart.getLineItems().remove(lineItem);
			BigDecimal cartValue = cart.getTotalCartValue();
			cartValue = cartValue.subtract(totalCost);
			cart.setTotalCartValue(cartValue);
			cartRepository.save(cart);
		} else
			throw new CartNotFoundException("Cart not found with key " + lineItemRequest.getCart().getCartKey());
	}

	/**
	 * Validates that the cart can be modified. Throws CartNotFoundException if the
	 * cart is not found. Throws CartModificationException if the cart is not in
	 * ACTIVE state.
	 *
	 * @param cartKey the key of the cart to validate
	 * @return the validated Cart object
	 */
	@Override
	public Cart validateCartState(String cartKey) {
		Cart cart = cartRepository.findCartByCartKey(cartKey);
		if (cart != null) {
			if (cart.getCartState() != CartState.ACTIVE) {
				// Throw Custom Exception
				throw new CartModificationException("Cannot modify a cart that is not in ACTIVE state.");
			}
			return cart;
		} else
			throw new CartNotFoundException("Cart not found with key " + cartKey);
	}
}
