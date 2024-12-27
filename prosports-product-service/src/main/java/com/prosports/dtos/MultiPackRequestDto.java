package com.prosports.dtos;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MultiPackRequestDto {

	@NotBlank(message = "MultiPack name cannot be blank")
	private String multiPackName;
	
	@NotBlank(message = "MultiPack description cannot be blank")
	private String multiPackDescription;
	
	@NotBlank(message = "MultiPack SKU cannot be blank")
	private String multiPackSku;
	private BigDecimal multiPackPrice;
	
	@NotNull(message = "MultiPack stock cannot be null")
    @Positive(message = "MultiPack stock must be positive")
	private Integer multiPackStock;
	
	@NotNull(message = "Product variants cannot be null")
	private List<ProductVariantRequestDto> productVariant;
}
