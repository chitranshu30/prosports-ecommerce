package com.prosports.dto;

import java.math.BigDecimal;
import java.util.List;

import com.prosports.model.CartState;
import com.prosports.model.LineItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartResponse {

	private String cartKey;

	private String customerUserName;
	private List<LineItem> lineItems;
	private BigDecimal totalCartValue;
	private CartState cartState;
}
