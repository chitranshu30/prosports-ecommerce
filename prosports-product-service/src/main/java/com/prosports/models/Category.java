package com.prosports.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@NotNull
	@Column(unique = true)
	private String categoryName;
	private String categoryDescription;

	@ManyToOne
	@JsonBackReference
	private Category parentCategory;

	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Category> subCategories;

	public void addSubCategory(Category subCategory) {
		subCategories.add(subCategory);
		subCategory.setParentCategory(this); // Ensure bidirectional consistency
	}

	public void removeSubCategory(Category subCategory) {
		subCategories.remove(subCategory);
		subCategory.setParentCategory(null); // Ensure bidirectional consistency
	}
}
