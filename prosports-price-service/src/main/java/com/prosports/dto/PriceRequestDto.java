package com.prosports.dto;

import java.math.BigDecimal;

import com.prosports.entity.Country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriceRequestDto {

	@NotBlank(message = "Product variant SKU cannot be blank")
	private String productVariantsku;
	
	private String customerGroup;
	
	@NotNull(message = "Country Name cannot be null")
	private Country country;
	
	@NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
	private BigDecimal amount;

}
