package com.prosports.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryNameRequestDto {

	@NotBlank(message = "Category name must not be blank")
	@Size(min = 2, max = 100, message = "Category name must not be less than 2 characters and not exceed 100 characters")
	private String categoryName;
	
}
