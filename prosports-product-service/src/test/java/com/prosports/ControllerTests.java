package com.prosports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prosports.controllers.CategoryController;
import com.prosports.controllers.MultiPackController;
import com.prosports.controllers.ProductController;
import com.prosports.controllers.ProductTypeController;
import com.prosports.dtos.CategoryRequestDto;
import com.prosports.dtos.CategoryResponseDto;
import com.prosports.dtos.MultiPackRequestDto;
import com.prosports.dtos.MultiPackResponseDto;
import com.prosports.dtos.ProductRequestDto;
import com.prosports.dtos.ProductResponseDto;
import com.prosports.dtos.ProductTypeRequestDto;
import com.prosports.dtos.ProductTypeResponseDto;
import com.prosports.dtos.ProductVariantResponseDto;
import com.prosports.service.CategoryService;
import com.prosports.service.MultiPackService;
import com.prosports.service.ProductService;
import com.prosports.service.ProductTypeService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ControllerTests {

	@InjectMocks
	private CategoryController categoryController;

	@InjectMocks
	private MultiPackController multiPackController;

	@InjectMocks
	private ProductController productController;

	@InjectMocks
	private ProductTypeController productTypeController;

	@Mock
	private CategoryService categoryService;

	@Mock
	private MultiPackService multiPackService;

	@Mock
	private ProductService productService;

	@Mock
	private ProductTypeService productTypeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// CategoryController Tests
	@Test
	void testFindCategoryByName() {
		CategoryResponseDto responseDto = new CategoryResponseDto();
		when(categoryService.findCategoryByName("Sports")).thenReturn(responseDto);

		ResponseEntity<CategoryResponseDto> response = categoryController.findCatgoryById("Sports");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testFindAllCategories() {
		List<CategoryResponseDto> responseList = Arrays.asList(new CategoryResponseDto());
		when(categoryService.findAllCategories()).thenReturn(responseList);

		ResponseEntity<List<CategoryResponseDto>> response = categoryController.findAllCategories();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isEmpty());
	}

	@Test
	void testCreateCategory() {
		CategoryRequestDto requestDto = new CategoryRequestDto();
		CategoryResponseDto responseDto = new CategoryResponseDto();
		when(categoryService.createCategory(requestDto)).thenReturn(responseDto);

		ResponseEntity<CategoryResponseDto> response = categoryController.createCategory(requestDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testUpdateCategory() {
		CategoryRequestDto requestDto = new CategoryRequestDto();
		CategoryResponseDto responseDto = new CategoryResponseDto();
		when(categoryService.updateCategory(requestDto)).thenReturn(responseDto);

		ResponseEntity<CategoryResponseDto> response = categoryController.updateCategory(requestDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	// MultiPackController Tests
	@Test
	void testCreateMultiPack() {
		MultiPackRequestDto requestDto = new MultiPackRequestDto();
		MultiPackResponseDto responseDto = new MultiPackResponseDto();
		when(multiPackService.createMultiPack(requestDto)).thenReturn(responseDto);

		ResponseEntity<MultiPackResponseDto> response = multiPackController.createMultiPack(requestDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testFindAllMultiPack() {
		List<MultiPackResponseDto> responseList = Arrays.asList(new MultiPackResponseDto());
		when(multiPackService.findAllMultiPack()).thenReturn(responseList);

		ResponseEntity<List<MultiPackResponseDto>> response = multiPackController.findAllMultiPack();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isEmpty());
	}

	// ProductController Tests
	@Test
	void testFindProductByName() {
		ProductResponseDto responseDto = new ProductResponseDto();
		when(productService.findProductByName("Ball")).thenReturn(responseDto);

		ResponseEntity<ProductResponseDto> response = productController.findProductByName("Ball");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testCreateProduct() {
		ProductRequestDto requestDto = new ProductRequestDto();
		ProductResponseDto responseDto = new ProductResponseDto();
		when(productService.createProduct(requestDto)).thenReturn(responseDto);

		ResponseEntity<ProductResponseDto> response = productController.createProduct(requestDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testFindProductVariantBySku() {
		ProductVariantResponseDto responseDto = new ProductVariantResponseDto();
		when(productService.findProductVariant("SKU123")).thenReturn(responseDto);

		ResponseEntity<ProductVariantResponseDto> response = productController.findProductVariantBySku("SKU123");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testGetRecommendation() {
		List<ProductVariantResponseDto> responseList = Arrays.asList(new ProductVariantResponseDto());
		when(productService.recommendation("SKU123")).thenReturn(responseList);

		ResponseEntity<List<ProductVariantResponseDto>> response = productController.getRecommendation("SKU123");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isEmpty());
	}

	// ProductTypeController Tests
	@Test
	void testFindProductTypeByName() {
		ProductTypeResponseDto responseDto = new ProductTypeResponseDto();
		when(productTypeService.findProductTypeByName("Equipment")).thenReturn(responseDto);

		ResponseEntity<ProductTypeResponseDto> response = productTypeController.findProductTypeByName("Equipment");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	void testFindAllProductType() {
		List<ProductTypeResponseDto> responseList = Arrays.asList(new ProductTypeResponseDto());
		when(productTypeService.findAllProductType()).thenReturn(responseList);

		ResponseEntity<List<ProductTypeResponseDto>> response = productTypeController.findAllProductType();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isEmpty());
	}

	@Test
	void testCreateProductType() {
		ProductTypeRequestDto requestDto = new ProductTypeRequestDto();
		ProductTypeResponseDto responseDto = new ProductTypeResponseDto();
		when(productTypeService.createProductType(requestDto)).thenReturn(responseDto);

		ResponseEntity<ProductTypeResponseDto> response = productTypeController.createProductType(requestDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}
}