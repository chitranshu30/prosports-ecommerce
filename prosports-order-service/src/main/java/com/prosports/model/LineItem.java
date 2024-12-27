package com.prosports.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LineItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lineItemId;
	
	@Column(nullable = false)
	private String lineItemSku;	
	
	private Integer lineItemQuantity;
	
	@Column(nullable = false)
	private BigDecimal lineItemPrice;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	@JsonBackReference
	private Cart cart;
	
}
