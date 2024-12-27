package com.prosports.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryRequestDto {

	@NotBlank(message = "Country name cannot be blank")
	private String countryName;

	@NotBlank(message = "Country currency cannot be blank")
	private String countryCurrency;

}
