package com.prosports.service;

import java.util.List;

import com.prosports.dtos.ProductRequestDto;
import com.prosports.dtos.ProductResponseDto;
import com.prosports.dtos.ProductVariantResponseDto;

public interface ProductService {

	ProductResponseDto findProductByName(String productName);

	ProductResponseDto createProduct(ProductRequestDto productRequestDto);

	ProductVariantResponseDto findProductVariant(String productVariantSku);

	List<ProductVariantResponseDto> recommendation(String ProductVariantSku);

}