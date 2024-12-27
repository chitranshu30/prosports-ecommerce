package com.prosports.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceResponseDto {

	private String productVariantsku;
	private String customerGroup;
	private CountryResponseDto country;
	private BigDecimal amount;

}
