package com.prosports.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class PaymentMethodInfo {
	@Enumerated(EnumType.STRING)
	private PaymentMethodType type;
}
