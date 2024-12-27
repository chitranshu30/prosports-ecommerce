package com.prosports.entity;

import java.math.BigDecimal;

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
public class Price {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long priceId;

	private String productVariantsku;
	private String customerGroup;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	private BigDecimal amount;

}
