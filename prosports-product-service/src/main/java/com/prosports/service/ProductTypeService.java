package com.prosports.service;

import java.util.List;

import com.prosports.dtos.ProductTypeRequestDto;
import com.prosports.dtos.ProductTypeResponseDto;

public interface ProductTypeService {

	ProductTypeResponseDto findProductTypeByName(String productTypeName);

	List<ProductTypeResponseDto> findAllProductType();

	ProductTypeResponseDto createProductType(ProductTypeRequestDto productTypeRequestDto);

}