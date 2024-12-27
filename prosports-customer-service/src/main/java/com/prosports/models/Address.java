package com.prosports.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {
	@NotBlank(message = "City is Mandatory")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "City must only contain letters")

	private String city;

	@NotBlank(message = "Country is Mandatory")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Country must only contain letters")
	private String country;
}
