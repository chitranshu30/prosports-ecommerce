package com.prosports.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dtos.OnlyPriceResponseDto;
import com.prosports.dtos.ProductRequestDto;
import com.prosports.dtos.ProductResponseDto;
import com.prosports.dtos.ProductVariantResponseDto;
import com.prosports.exceptions.CategoryNotFoundException;
import com.prosports.exceptions.ProductAlreadyExistException;
import com.prosports.exceptions.ProductNotFoundException;
import com.prosports.exceptions.ProductTypeNotFoundException;
import com.prosports.exceptions.ProductVariantNotFoundException;
import com.prosports.models.AttributeDefinition;
import com.prosports.models.AttributeType;
import com.prosports.models.Category;
import com.prosports.models.Product;
import com.prosports.models.ProductType;
import com.prosports.models.ProductVariant;
import com.prosports.openFeign.PriceServiceFeign;
import com.prosports.repositories.CategoryRepository;
import com.prosports.repositories.ProductRepository;
import com.prosports.repositories.ProductTypeRepository;
import com.prosports.repositories.ProductVarientRepository;
import com.prosports.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductVarientRepository productVarientRepository;

	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PriceServiceFeign priceServiceFeign;

	/**
	 * Finds a product by its name. Throws ProductNotFoundException if the product
	 * is not found.
	 *
	 * @param productName the name of the product to find
	 * @return a ProductResponseDto containing the product details
	 */
	@Override
	public ProductResponseDto findProductByName(String productName) {
		Product product = productRepository.findProductByProductName(productName);
		if (product != null) {
			logger.info("Product found with name {}", productName);
			return modelMapper.map(product, ProductResponseDto.class);
		}
		else {			
			logger.error("Product not found with name: {}", productName);
			throw new ProductNotFoundException("Product Not found with product name " + productName);
		}
	}

	/**
	 * Creates a new product. Throws ProductAlreadyExistException if a product with
	 * the same name already exists. Throws CategoryNotFoundException if any
	 * category is not found. Throws ProductTypeNotFoundException if the product
	 * type is not found.
	 *
	 * @param productRequestDto the data transfer object containing the details of
	 *                          the product to be created
	 * @return a ProductResponseDto containing the details of the created product
	 */
	@Override
	public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
		if (!productRepository.existsByProductName(productRequestDto.getProductName())) {
			Product product = new Product();
			product.setProductName(productRequestDto.getProductName());
			product.setProductDescription(productRequestDto.getProductDescription());
			ProductType productType = productTypeRepository
					.findProductTypeByProductTypeName(productRequestDto.getProductType().getProductTypeName());
			if (productType != null) {
				product.setProductType(productType);
				for (Category eachCategory : productRequestDto.getCategory()) {
					Category category = categoryRepository.findCategoryByCategoryName(eachCategory.getCategoryName());
					if (category != null)
						product.addCategory(category);
					else {
						logger.error("Category not found with name: {}", eachCategory.getCategoryName());
						throw new CategoryNotFoundException(
								"No Category found with name " + eachCategory.getCategoryName());
					}
				}
				productRepository.save(product);
				for (ProductVariant eachProductVariant : productRequestDto.getVariants()) {
					createVariant(eachProductVariant.getProductVariantSku(), eachProductVariant.getVariantAttributes(),
							eachProductVariant.getProductVariantStock(), eachProductVariant.getProductVariantPrice(),
							product);
				}
				productRepository.save(product); // Saving again to persist all the changes made to variants list
				logger.info("Product created with name {}", product.getProductName());
				return modelMapper.map(product, ProductResponseDto.class);
			} else {
				logger.error("Product type not found with name: {}", productRequestDto.getProductType().getProductTypeName());
				throw new ProductTypeNotFoundException(
						"No Product Type found with name " + productRequestDto.getProductType().getProductTypeName());
			}
		} else {
			logger.error("Product already exists with name: {}", productRequestDto.getProductName());
			throw new ProductAlreadyExistException(
					"Product Already Exists with name " + productRequestDto.getProductName());
		}
	}

	/**
	 * Creates a new product variant and adds it to the given product. Validates the
	 * variant attributes against the product type.
	 *
	 * @param sku               the SKU of the product variant
	 * @param variantAttributes the attributes of the product variant
	 * @param stock             the stock of the product variant
	 * @param price             the price of the product variant
	 * @param product           the product to which the variant belongs
	 */
	private void createVariant(String sku, Map<String, String> variantAttributes, Integer stock, BigDecimal price,
			Product product) {
		// Validate variant attributes against product type
		validateVariantAttributes(variantAttributes, product.getProductType());

		ProductVariant variant = new ProductVariant();
		variant.setProductVariantSku(sku);
		variant.setVariantAttributes(variantAttributes);
		variant.setProductVariantStock(stock);
		variant.setProductVariantPrice(price);
		variant.setProduct(product);
		productVarientRepository.save(variant);
		product.addVarient(variant);
		logger.info("Variant created with Sku {}", variant.getProductVariantSku());
	}

	/**
	 * Validates the variant attributes against the product type. Ensures required
	 * attributes are provided and validates allowed values for ENUM attributes.
	 *
	 * @param variantAttributes the attributes of the product variant
	 * @param productType       the product type to validate against
	 */
	private void validateVariantAttributes(Map<String, String> variantAttributes, ProductType productType) {
		for (AttributeDefinition attribute : productType.getAttributes()) {
			String attributeName = attribute.getAttributeName();
			String attributeValue = variantAttributes.get(attributeName);

			// Ensure required attributes are provided
			if (attributeValue == null) {
				logger.error("Missing value for required attribute: {}", attributeName);
				throw new IllegalArgumentException("Missing value for required attribute: " + attributeName);
			}

			// If ENUM, validate allowed values
			if (attribute.getAttributeType() == AttributeType.ENUM) {
				logger.error("Invalid value for attribute {}: {}", attributeName, attributeValue);
				if (!attribute.getAllowedValues().contains(attributeValue)) {
					throw new IllegalArgumentException(
							"Invalid value for attribute " + attributeName + ": " + attributeValue);
				}
			}
		}
	}

	/**
	 * Finds a product variant by its SKU. Throws ProductVariantNotFoundException if
	 * the product variant is not found.
	 *
	 * @param productVariantSku the SKU of the product variant to find
	 * @return a ProductVariantResponseDto containing the product variant details
	 */
	@Override
	public ProductVariantResponseDto findProductVariant(String productVariantSku) {
		ProductVariant productVariant = productVarientRepository
				.findProductVariantByProductVariantSku(productVariantSku);
		if (productVariant != null) {
			OnlyPriceResponseDto priceResponse = priceServiceFeign.findPriceBySku(productVariantSku);
			productVariant.setProductVariantPrice(priceResponse.getAmount());
			logger.info("Product variant found with Sku: {}", productVariantSku);
			return modelMapper.map(productVariant, ProductVariantResponseDto.class);
		} else {
			logger.error("Product variant not found with SKU: {}", productVariantSku);
			throw new ProductVariantNotFoundException("No product variant found with sku " + productVariantSku);
		}
	}

	/**
	 * Provides product variant recommendations based on the categories of the given
	 * product variant. Throws ProductVariantNotFoundException if the product
	 * variant is not found. Throws CategoryNotFoundException if no categories are
	 * found for the product variant.
	 *
	 * @param productVariantSku the SKU of the product variant to base
	 *                          recommendations on
	 * @return a list of ProductVariantResponseDto containing the recommended
	 *         product variants
	 */
	@Override
	public List<ProductVariantResponseDto> recommendation(String productVariantSku) {
		ProductVariant productVariant = productVarientRepository
				.findProductVariantByProductVariantSku(productVariantSku);
		if (productVariant != null) {
			List<Category> categoryList = productVariant.getProduct().getCategories();
			if (!categoryList.isEmpty()) {
				List<ProductVariant> variantsInSameCategories = productVarientRepository.findAll().stream().filter(
						variant -> variant.getProduct().getCategories().stream().anyMatch(categoryList::contains))
						.filter(variant -> !variant.getProductVariantSku().equals(productVariantSku))
						.collect(Collectors.toList());
				logger.info("Recommendations found {}");
				return variantsInSameCategories.stream()
						.map(variant -> modelMapper.map(variant, ProductVariantResponseDto.class))
						.collect(Collectors.toList());
			} else {
				logger.error("No categories found for the product of product variant with SKU: {}", productVariantSku);
				throw new CategoryNotFoundException(
						"No Category found for the product of Product Variant with sku " + productVariantSku);
			}
		} else {
			logger.error("Product variant not found with Sku: {}", productVariantSku);
			throw new ProductVariantNotFoundException("No Product Variant found with sku " + productVariantSku);
		}
	}
}
