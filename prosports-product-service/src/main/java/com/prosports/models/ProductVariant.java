package com.prosports.models;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductVariant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productVariantId;

	@Column(unique = true)
	private String productVariantSku;

	@ElementCollection
	private Map<String, String> variantAttributes;
	private BigDecimal productVariantPrice;
	private Integer productVariantStock;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@JsonBackReference
	private Product product;

}
