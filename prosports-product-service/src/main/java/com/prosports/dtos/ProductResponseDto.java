package com.prosports.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {

	private String productName;
	private String productDescription;
	private ProductTypeResponseDto productType;
	private List<CategoryResponseDto> categories = new ArrayList<>();
	private List<ProductVariantResponseDto> variants = new ArrayList<>();

}
