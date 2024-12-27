package com.prosports.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductVariantRequestDto {

	@NotNull(message = "Product Variant Sku cannot be null")
	private String productVariantSku;

}
