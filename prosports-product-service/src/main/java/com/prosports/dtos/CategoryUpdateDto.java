package com.prosports.dtos;

import com.prosports.models.Category;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryUpdateDto {

	@Size(max = 255, message = "Description must be less than 255 characters")
	private String categoryDescription;
	private Category parentCategory;

}
