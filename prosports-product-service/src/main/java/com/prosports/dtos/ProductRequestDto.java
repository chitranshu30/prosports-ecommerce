package com.prosports.dtos;

import java.util.ArrayList;
import java.util.List;

import com.prosports.models.Category;
import com.prosports.models.ProductType;
import com.prosports.models.ProductVariant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDto {

	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
	private String productName;

	@Size(max = 255, message = "Description must be less than 255 characters")
	private String productDescription;

	@NotNull(message = "Product type is mandatory")
	private ProductType productType;

	@NotNull(message = "Category list cannot be null")
	private List<@NotNull Category> category = new ArrayList<>();

	@NotNull(message = "Variants list cannot be null")
	private List<@NotNull ProductVariant> variants = new ArrayList<>();

}
