package com.prosports.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductVariantResponseDto {

	private String productVariantSku;
	private Map<String, String> variantAttributes;
	private BigDecimal productVariantPrice;

}
