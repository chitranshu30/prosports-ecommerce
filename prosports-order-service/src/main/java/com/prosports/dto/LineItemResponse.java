package com.prosports.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LineItemResponse {

	private String lineItemSku;
	private int lineItemQuantity;
	private BigDecimal lineItemPrice;
}
