package com.prosports.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dtos.CategoryNameRequestDto;
import com.prosports.dtos.CategoryRequestDto;
import com.prosports.dtos.CategoryResponseDto;
import com.prosports.exceptions.CategoryAlreadyExistsException;
import com.prosports.exceptions.CategoryNotFoundException;
import com.prosports.models.Category;
import com.prosports.repositories.CategoryRepository;
import com.prosports.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Finds a category by its name. Throws CategoryNotFoundException if the
	 * category is not found.
	 *
	 * @param categoryName the name of the category to find
	 * @return a CategoryResponseDto containing the category details
	 */
	@Override
	public CategoryResponseDto findCategoryByName(String categoryName) {
		Category category = categoryRepository.findCategoryByCategoryName(categoryName);
		if (category != null) {
			logger.info("Category found with name {}", categoryName);
			return modelMapper.map(category, CategoryResponseDto.class);
		} else {
			logger.error("No Category found with name {}", categoryName);
			throw new CategoryNotFoundException("No Category found with name " + categoryName);
		}
	}

	/**
	 * Finds all categories. Throws CategoryNotFoundException if no categories are
	 * found. Used Only for testing.
	 *
	 * @return a list of CategoryResponseDto containing the details of all
	 *         categories
	 */
	@Override
	public List<CategoryResponseDto> findAllCategories() {
		List<Category> categoryList = categoryRepository.findAll();
		if (categoryList != null) {
			logger.info("Categories found");
			return categoryList.stream().map(category -> modelMapper.map(category, CategoryResponseDto.class))
					.collect(Collectors.toList());
		}else {
			logger.error("No Category exists in the database");
			throw new CategoryNotFoundException("No Category exists in the database");
		}
	}

	/**
	 * Creates a new category. Throws CategoryAlreadyExistsException if a category
	 * with the same name already exists.
	 *
	 * @param categoryRequestDto the data transfer object containing the details of
	 *                           the category to be created
	 * @return a CategoryResponseDto containing the details of the created category
	 */
	@Override
	public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
		Category existingCategory = categoryRepository.findCategoryByCategoryName(categoryRequestDto.getCategoryName());
		if (existingCategory == null) {
			Category category = modelMapper.map(categoryRequestDto, Category.class);
			categoryRepository.save(category);
			logger.info("Category created with name {}", category.getCategoryName());
			return modelMapper.map(category, CategoryResponseDto.class);
		} else {
			logger.error("Category with name {} already exists", categoryRequestDto.getCategoryName());
			throw new CategoryAlreadyExistsException(
					"Category with name " + categoryRequestDto.getCategoryName() + " already exists.");
		}
	}

	/**
	 * Updates an existing category. Throws CategoryNotFoundException if the
	 * category is not found.
	 *
	 * @param categoryRequestDto the data transfer object containing the updated
	 *                           details of the category
	 * @return a CategoryResponseDto containing the updated category details
	 */
	@Override
	public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto) {
		Category category = categoryRepository.findCategoryByCategoryName(categoryRequestDto.getCategoryName());
		if (category != null) {
			modelMapper.typeMap(CategoryRequestDto.class, Category.class)
					.addMappings(mapper -> mapper.skip(Category::setCategoryId));
			modelMapper.map(categoryRequestDto, category);
			categoryRepository.save(category);
			logger.info("Category updated with name {}", category.getCategoryName());
			return modelMapper.map(category, CategoryResponseDto.class);
		} else {
			logger.error("No Category found with name ", categoryRequestDto.getCategoryName());
			throw new CategoryNotFoundException("No Category found with name " + categoryRequestDto.getCategoryName());
		}
	}

	/**
	 * Deletes a category by its name. Throws CategoryNotFoundException if the
	 * category is not found.
	 *
	 * @param categoryNameRequestDto the data transfer object containing the name of
	 *                               the category to be deleted
	 * @return a message indicating the result of the deletion
	 */
	@Override
	public String deleteCategoryById(CategoryNameRequestDto categoryNameRequestDto) {
		CategoryResponseDto categoryResponseDto = findCategoryByName(categoryNameRequestDto.getCategoryName());
		if (categoryResponseDto != null) {
			Long categoryId = categoryRepository.findCategoryByCategoryName(categoryNameRequestDto.getCategoryName())
					.getCategoryId();
			categoryRepository.deleteById(categoryId);
			logger.info("Category deleted with name: {}", categoryNameRequestDto.getCategoryName());
			return "Category has been deleted with name " + categoryNameRequestDto.getCategoryName();
		} else {
			logger.error("No Category found with name {}", categoryNameRequestDto.getCategoryName());
			throw new CategoryNotFoundException(
					"No Category found with name " + categoryNameRequestDto.getCategoryName());
		}
	}

	/**
	 * Adds a sub-category to an existing parent category. Throws
	 * CategoryNotFoundException if the parent category is not found.
	 *
	 * @param categoryRequestDto the data transfer object containing the details of
	 *                           the sub-category to be added
	 * @return a CategoryResponseDto containing the details of the added
	 *         sub-category
	 */
	@Override
	public CategoryResponseDto addSubCategory(CategoryRequestDto categoryRequestDto) {
		Category parentCategory = categoryRepository
				.findCategoryByCategoryName(categoryRequestDto.getParentCategory().getCategoryName());
		if (parentCategory != null) {
			modelMapper.typeMap(CategoryRequestDto.class, Category.class)
					.addMappings(mapper -> mapper.skip(Category::setCategoryId));
			Category category = modelMapper.map(categoryRequestDto, Category.class);
			parentCategory.addSubCategory(category);
			categoryRepository.save(parentCategory);
			logger.info("Sub-category added with name {}", category.getCategoryName());
			return modelMapper.map(category, CategoryResponseDto.class);
		} else {
			logger.error("No Parent Category found with name {}", categoryRequestDto.getParentCategory().getCategoryName());
			throw new CategoryNotFoundException(
					"No Parent Category found with name " + categoryRequestDto.getParentCategory().getCategoryName());
		}
	}

	/**
	 * Removes a sub-category by its name. Throws CategoryNotFoundException if the
	 * sub-category or parent category is not found.
	 *
	 * @param categoryNameRequestDto the data transfer object containing the name of
	 *                               the sub-category to be removed
	 * @return a message indicating the result of the removal
	 */
	@Override
	public String removeSubCategory(CategoryNameRequestDto categoryNameRequestDto) {
		Category category = categoryRepository.findCategoryByCategoryName(categoryNameRequestDto.getCategoryName());
		if (category != null) {
			String categoryName = category.getCategoryName();
			Category parentCategory = categoryRepository
					.findCategoryByCategoryName(category.getParentCategory().getCategoryName());
			if (parentCategory != null) {
				parentCategory.removeSubCategory(category);
				categoryRepository.save(parentCategory); // Save both parent and sub-category
				logger.info("Sub-category removed with name: {}", categoryName);
				return "SubCategory has been deleted with name " + categoryName;
			} else {
				logger.error("No Parent Category found for Sub-Category {}", categoryNameRequestDto.getCategoryName());
				throw new CategoryNotFoundException(
						"No Parent Category found for Sub-Category " + categoryNameRequestDto.getCategoryName());
			}
		} else {
			logger.error("No Category found with name {}", categoryNameRequestDto.getCategoryName());
			throw new CategoryNotFoundException(
					"No Category found with name " + categoryNameRequestDto.getCategoryName());
		}
	}

	/**
	 * Updates an existing sub-category. Throws CategoryNotFoundException if the
	 * sub-category or parent category is not found.
	 *
	 * @param categoryRequestDto the data transfer object containing the updated
	 *                           details of the sub-category
	 * @return a CategoryResponseDto containing the updated sub-category details
	 */
	@Override
	public CategoryResponseDto updateSubCategory(CategoryRequestDto categoryRequestDto) {
		Category category = categoryRepository.findCategoryByCategoryName(categoryRequestDto.getCategoryName());
		if (category != null) {
			Category parentCategory = category.getParentCategory();
			if (parentCategory != null) {
				modelMapper.typeMap(CategoryRequestDto.class, Category.class)
						.addMappings(mapper -> mapper.skip(Category::setCategoryId))
						.addMappings(mapper -> mapper.skip(Category::setParentCategory));
				modelMapper.map(categoryRequestDto, category);
				categoryRepository.save(category);
				logger.info("Sub-category updated with name {}", category.getCategoryName());
				return modelMapper.map(category, CategoryResponseDto.class);
			} else {
				logger.error("No Parent Category found for Sub-Category {}", categoryRequestDto.getCategoryName());
				throw new CategoryNotFoundException(
						"No Parent Category found for Sub-Category " + categoryRequestDto.getCategoryName());
			}
		} else {
			logger.error("No Category found with name {}", categoryRequestDto.getCategoryName());
			throw new CategoryNotFoundException("No Category found with name " + categoryRequestDto.getCategoryName());
		}
	}

}
