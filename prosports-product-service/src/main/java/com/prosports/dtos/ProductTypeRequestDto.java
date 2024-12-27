package com.prosports.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductTypeRequestDto {

	@NotBlank(message = "Product Type Name is mandatory")
	@Size(min = 2, max = 100, message = "Product Type name must not be less than 2 characters and not exceed 100 characters")
	private String productTypeName;

	@Size(max = 255, message = "Description must be less than 255 characters")
	private String productTypeDescription;

	@NotNull(message = "Attribute Definition list cannot be null")
	private List<@NotNull AttributeDefinitionResponceDto> attributes = new ArrayList<>();

}
