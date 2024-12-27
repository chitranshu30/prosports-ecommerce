package com.prosports.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders") // To avoid conflict with SQL reserved keywords
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId; // Unique identifier for the order

	private String orderKey;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentState paymentState; // Tracks payment status (PENDING, PAID, FAILED)

	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@Column(name = "created_at")
	private LocalDateTime createdAt; // Added createdAt field

	@PrePersist
	public void prePersist() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
	}
}