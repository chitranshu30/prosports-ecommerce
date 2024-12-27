package com.prosports.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dtos.ProductTypeRequestDto;
import com.prosports.dtos.ProductTypeResponseDto;
import com.prosports.service.ProductTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product-type")
public class ProductTypeController {

	@Autowired
	private ProductTypeService productTypeService;

	@GetMapping("/findByName/{productTypeName}")
	public ResponseEntity<ProductTypeResponseDto> findProductTypeByName(@Valid @PathVariable String productTypeName) {
		return new ResponseEntity<ProductTypeResponseDto>(productTypeService.findProductTypeByName(productTypeName),
				HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<ProductTypeResponseDto>> findAllProductType() {
		return new ResponseEntity<List<ProductTypeResponseDto>>(productTypeService.findAllProductType(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ProductTypeResponseDto> createProductType(
			@Valid @RequestBody ProductTypeRequestDto productTypeRequestDto) {
		return new ResponseEntity<ProductTypeResponseDto>(productTypeService.createProductType(productTypeRequestDto),
				HttpStatus.CREATED);
	}

}
