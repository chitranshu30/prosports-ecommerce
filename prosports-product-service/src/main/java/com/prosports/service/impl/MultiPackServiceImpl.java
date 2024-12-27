package com.prosports.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dtos.MultiPackRequestDto;
import com.prosports.dtos.MultiPackResponseDto;
import com.prosports.dtos.ProductVariantRequestDto;
import com.prosports.exceptions.ProductVariantNotFoundException;
import com.prosports.models.MultiPack;
import com.prosports.models.ProductVariant;
import com.prosports.repositories.MultiPackRepository;
import com.prosports.repositories.ProductVarientRepository;
import com.prosports.service.MultiPackService;

@Service
public class MultiPackServiceImpl implements MultiPackService {

	private static final Logger logger = LoggerFactory.getLogger(MultiPackServiceImpl.class);
	
	@Autowired
	private MultiPackRepository multiPackRepository;
	
	@Autowired
	private ProductVarientRepository productVarientRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
     * Creates a new MultiPack. Converts the MultiPackRequestDto to a MultiPack entity,
     * adds the product variants to the MultiPack, and saves it to the repository.
     * Throws ProductVariantNotFoundException if any product variant is not found.
     *
     * @param multiPackRequestDto the data transfer object containing the details of the MultiPack to be created
     * @return a MultiPackResponseDto containing the details of the created MultiPack
     */
	@Override
	public MultiPackResponseDto createMultiPack(MultiPackRequestDto multiPackRequestDto) {
		MultiPack multiPack = modelMapper.map(multiPackRequestDto, MultiPack.class);
		for(ProductVariantRequestDto eachVariant : multiPackRequestDto.getProductVariant()) {
			ProductVariant productVariant =  productVarientRepository.findProductVariantByProductVariantSku(eachVariant.getProductVariantSku());
			if (productVariant == null) {
				logger.error("Product variant with SKU {} not found", eachVariant.getProductVariantSku());
                throw new ProductVariantNotFoundException("Product variant with SKU " + eachVariant.getProductVariantSku() + " not found.");
            }
			multiPack.addProductVariant(productVariant);
		}
		multiPackRepository.save(multiPack);
		logger.info("MultiPack created with name {}", multiPack.getMultiPackName());
		return modelMapper.map(multiPack, MultiPackResponseDto.class);
	}
	
	/**
     * Finds all MultiPacks. Converts the list of MultiPack entities to a list of MultiPackResponseDto.
     *
     * @return a list of MultiPackResponseDto containing the details of all MultiPacks
     */
	@Override
	public List<MultiPackResponseDto> findAllMultiPack() {
		List<MultiPack> multiPackList = multiPackRepository.findAll();
		logger.info("MultiPacks found");
		return multiPackList
				.stream()
				.map(multiPack -> modelMapper.map(multiPack, MultiPackResponseDto.class))
				.collect(Collectors.toList());
	}
	
}
