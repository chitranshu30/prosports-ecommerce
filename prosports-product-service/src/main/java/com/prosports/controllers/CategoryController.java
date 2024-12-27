package com.prosports.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dtos.CategoryNameRequestDto;
import com.prosports.dtos.CategoryRequestDto;
import com.prosports.dtos.CategoryResponseDto;
import com.prosports.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/findCategoryByName/{categoryName}")
	public ResponseEntity<CategoryResponseDto> findCatgoryById(
			@Valid @PathVariable String categoryName) {
		return new ResponseEntity<CategoryResponseDto>(categoryService.findCategoryByName(categoryName),
				HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<CategoryResponseDto>> findAllCategories() {
		return new ResponseEntity<List<CategoryResponseDto>>(categoryService.findAllCategories(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
		return new ResponseEntity<CategoryResponseDto>(categoryService.createCategory(categoryRequestDto),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCatgory(@Valid @RequestBody CategoryNameRequestDto categoryNameRequestDto) {
		return new ResponseEntity<String>(categoryService.deleteCategoryById(categoryNameRequestDto), HttpStatus.OK);
	}

	@PatchMapping("/update")
	public ResponseEntity<CategoryResponseDto> updateCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
		return new ResponseEntity<CategoryResponseDto>(categoryService.updateCategory(categoryRequestDto),
				HttpStatus.OK);
	}

	@PostMapping("/create/sub-category")
	public ResponseEntity<CategoryResponseDto> createSubCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
		return new ResponseEntity<CategoryResponseDto>(categoryService.addSubCategory(categoryRequestDto),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/sub-category")
	public ResponseEntity<String> deleteSubCatgory(@Valid @RequestBody CategoryNameRequestDto categoryNameRequestDto) {
		return new ResponseEntity<String>(categoryService.removeSubCategory(categoryNameRequestDto), HttpStatus.OK);
	}
	
	@PatchMapping("/update/sub-category")
	public ResponseEntity<CategoryResponseDto> updateSubCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
		return new ResponseEntity<CategoryResponseDto>(categoryService.updateSubCategory(categoryRequestDto),
				HttpStatus.OK);
	}

}
