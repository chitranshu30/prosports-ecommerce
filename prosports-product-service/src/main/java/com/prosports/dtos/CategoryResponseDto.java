package com.prosports.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prosports.models.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {

	private String categoryName;
	private String categoryDescription;
	
	@JsonIgnore
	private Category parentCategory;
	private List<CategoryResponseDto> subCategories;

}
