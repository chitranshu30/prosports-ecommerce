package com.prosports.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MultiPack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long multiPackId;

	@Column(unique = true)
	private String multiPackName;

	private String multiPackDescription;

	@Column(unique = true)
	private String multiPackSku;
	private BigDecimal multiPackPrice;
	private Integer multiPackStock;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "MultiPack_ProductVariant", joinColumns = @JoinColumn(name = "multiPackId"), inverseJoinColumns = @JoinColumn(name = "productVariantId"))
	private List<ProductVariant> productVariants = new ArrayList<ProductVariant>();

	public void addProductVariant(ProductVariant productVariant) {
		productVariants.add(productVariant);
	}

}
