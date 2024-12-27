package com.prosports.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prosportpayment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@Column(unique = true)
	private String key;

	@Column(unique = true)
	private String paymentReference;
	private BigDecimal amount;

	@Embedded
	private PaymentMethodInfo paymentMethodInfo;

	private LocalDateTime paymentDate;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

}
