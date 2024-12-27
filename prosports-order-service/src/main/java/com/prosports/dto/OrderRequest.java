package com.prosports.dto;

import com.prosports.model.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

	@NotNull(message = "Cart cannot be null")
	private Cart cart;

}
