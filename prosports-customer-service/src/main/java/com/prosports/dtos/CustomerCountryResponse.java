package com.prosports.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerCountryResponse {
	private String country;
	private String customerGroup; //PRIME & NON_PRIME
}
