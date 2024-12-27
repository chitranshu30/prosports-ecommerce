package com.prosports.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dtos.ProductTypeRequestDto;
import com.prosports.dtos.ProductTypeResponseDto;
import com.prosports.exceptions.ProductTypeAlreadyExistException;
import com.prosports.exceptions.ProductTypeNotFoundException;
import com.prosports.models.ProductType;
import com.prosports.repositories.ProductTypeRepository;
import com.prosports.service.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

	private static final Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	
	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Autowired
	private ModelMapper modelMapper;

	 /**
     * Finds a product type by its name. Throws ProductTypeNotFoundException if the product type is not found.
     *
     * @param productTypeName the name of the product type to find
     * @return a ProductTypeResponseDto containing the product type details
     */
	@Override
	public ProductTypeResponseDto findProductTypeByName(String productTypeName) {
		ProductType productType = productTypeRepository.findProductTypeByProductTypeName(productTypeName);
		if (productType != null) {
			logger.info("Product type found with name {}", productTypeName);
			return modelMapper.map(productType, ProductTypeResponseDto.class);
		}
		else {
			logger.error("Product type not found with name: {}", productTypeName);
			throw new ProductTypeNotFoundException("Product Type Not found with product type name " + productTypeName);
		}
	}

	/**
     * Finds all product types. Throws ProductTypeNotFoundException if no product types are found.
     *
     * @return a list of ProductTypeResponseDto containing the details of all product types
     */
	@Override
	public List<ProductTypeResponseDto> findAllProductType() {
		List<ProductType> productTypeList = productTypeRepository.findAll();
		if (productTypeList != null) {
			logger.info("Product types found");
			return productTypeList.stream()
					.map(productType -> modelMapper.map(productType, ProductTypeResponseDto.class))
					.collect(Collectors.toList());
		} else {
			logger.error("No product types exist in the database");
			throw new ProductTypeNotFoundException("No Product Type Exist in the database");
		}
	}

    /**
     * Creates a new product type. Throws ProductTypeAlreadyExistException if a product type with the same name already exists.
     *
     * @param productTypeRequestDto the data transfer object containing the details of the product type to be created
     * @return a ProductTypeResponseDto containing the details of the created product type
     */
	@Override
	public ProductTypeResponseDto createProductType(ProductTypeRequestDto productTypeRequestDto) {
		if (!productTypeRepository.existsByProductTypeName(productTypeRequestDto.getProductTypeName())) {
			ProductType createProductType = modelMapper.map(productTypeRequestDto, ProductType.class);
			productTypeRepository.save(createProductType);
			logger.info("Product type created with name {}", createProductType.getProductTypeName());
			return modelMapper.map(createProductType, ProductTypeResponseDto.class);
		} else {
			logger.error("Product type already exists with name {}", productTypeRequestDto.getProductTypeName());
			throw new ProductTypeAlreadyExistException(
					"Product Type already exists with name " + productTypeRequestDto.getProductTypeName());
		}
	}
}
