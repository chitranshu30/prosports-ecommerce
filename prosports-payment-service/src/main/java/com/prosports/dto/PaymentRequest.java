package com.prosports.dto;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {
	
	@NotNull(message = "Key cannot be null")
    @Column(unique = true)
	private String key;
	
	@NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
	private BigDecimal amount;
	
}