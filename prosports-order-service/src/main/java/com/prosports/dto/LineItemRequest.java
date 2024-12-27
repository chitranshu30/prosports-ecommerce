package com.prosports.dto;

import java.math.BigDecimal;

import com.prosports.model.Cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LineItemRequest {

	@NotBlank(message = "Line item SKU cannot be blank")
	private String lineItemSku;
	
	@NotNull(message = "Line item quantity cannot be null")
    @Positive(message = "Line item quantity must be positive")
	private Integer lineItemQuantity;
	
	private BigDecimal lineItemPrice;
	
	@NotNull(message = "Cart cannot be null")
	private Cart cart;

}
