package com.prosports.dtos;

import com.prosports.models.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDto {

	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 100, message = "Category name must not be less than 2 characters and not exceed 100 characters")
	private String categoryName;

	@Size(max = 255, message = "Description must be less than 255 characters")
	private String categoryDescription;

	private Category parentCategory;

}
