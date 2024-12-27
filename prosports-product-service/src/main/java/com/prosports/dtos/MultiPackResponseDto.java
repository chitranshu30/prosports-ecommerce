package com.prosports.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MultiPackResponseDto {

	private String multiPackName;
    private String multiPackDescription;
    private String multiPackSku;
    private BigDecimal multiPackPrice;
    private Integer multiPackStock;
    private List<ProductVariantResponseDto> productVariants;

}
