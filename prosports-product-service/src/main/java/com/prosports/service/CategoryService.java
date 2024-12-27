package com.prosports.service;

import java.util.List;

import com.prosports.dtos.CategoryNameRequestDto;
import com.prosports.dtos.CategoryRequestDto;
import com.prosports.dtos.CategoryResponseDto;

public interface CategoryService {

	CategoryResponseDto findCategoryByName(String categoryName);

	List<CategoryResponseDto> findAllCategories();

	CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

	CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto);

	String deleteCategoryById(CategoryNameRequestDto categoryNameRequestDto);

	CategoryResponseDto addSubCategory(CategoryRequestDto categoryRequestDto);

	String removeSubCategory(CategoryNameRequestDto categoryNameRequestDto);

	CategoryResponseDto updateSubCategory(CategoryRequestDto categoryRequestDto);

}