package com.prosports.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dto.CartResponse;
import com.prosports.dto.OnlyPriceResponseDto;
import com.prosports.dto.ProductVariantResponseDto;
import com.prosports.exceptions.CartNotFoundException;
import com.prosports.model.Cart;
import com.prosports.model.CartState;
import com.prosports.model.LineItem;
import com.prosports.openFeign.PriceServiceFeign;
import com.prosports.openFeign.ProductServiceFeign;
import com.prosports.repositories.CartRepository;
import com.prosports.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ProductServiceFeign productServiceFeign;
	
	@Autowired
	private PriceServiceFeign priceServiceFeign;

	/**
     * Creates a new cart. Initializes the cart with default values and saves it to the repository.
     *
     * @return a CartResponse containing the details of the created cart
     */
	@Override
	public CartResponse createNewCart() {
		Cart cart = new Cart();
//		lets suppose we get data form the customer 
		cart.setCustomerUserName("dhruv");
		cart.setCartState(CartState.ACTIVE); // Default for new one
		cart.setTotalCartValue(BigDecimal.valueOf(0.00)); // Default for empty cart
		Cart savedCart = cartRepository.save(cart); // Saving to generate the cartID to create a unique cartKey
		String cartKey = savedCart.getCustomerUserName() + savedCart.getCartId();
		cart.setCartKey(cartKey);
		cartRepository.save(cart); // Saving again to update the cartKey with the uniquely created cartKey
		return modelMapper.map(cart, CartResponse.class);
	}

	/**
     * Finds a cart by its key. Calculates the total cart value based on the line items.
     * Throws CartNotFoundException if the cart is not found.
     *
     * @param cartKey the key of the cart to find
     * @return a CartResponse containing the cart details
     */
	@Override
	public CartResponse findCartByKey(String cartKey) {
		Cart cart = cartRepository.findCartByCartKey(cartKey);
		if (cart != null) {
			List<LineItem> lineItemList = cart.getLineItems();
			BigDecimal cartValue = BigDecimal.valueOf(0.0);
			for (LineItem eachLineItem : lineItemList) {
				BigDecimal totalCost = eachLineItem.getLineItemPrice()
						.multiply(BigDecimal.valueOf(eachLineItem.getLineItemQuantity()));
				cartValue = cartValue.add(totalCost);
			}
			cart.setTotalCartValue(cartValue);
			return modelMapper.map(cart, CartResponse.class);
        } else
        	throw new CartNotFoundException("Cart not found with key " + cartKey);        	
	}
	
	/**
     * Gets product variant recommendations based on the given line item SKU.(product-service)
     * Fetches the price for each recommended product variant.(pricing-service)
     *
     * @param lineItemSku the SKU of the line item to base recommendations on
     * @return a list of ProductVariantResponseDto containing the recommended product variants
     */
	@Override
	public List<ProductVariantResponseDto> getRecommendation(String lineItemSku) {
		List<ProductVariantResponseDto> productRespose = productServiceFeign.getRecommendation(lineItemSku);
		for(ProductVariantResponseDto eachProductVariantResponseDto : productRespose) {
			OnlyPriceResponseDto priceResponseDto = priceServiceFeign.findPriceBySku(eachProductVariantResponseDto.getProductVariantSku());
			eachProductVariantResponseDto.setProductVariantPrice(priceResponseDto.getAmount());
		}
		return productRespose;
	}

}
