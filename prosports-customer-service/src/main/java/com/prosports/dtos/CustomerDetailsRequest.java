package com.prosports.dtos;

import java.time.LocalDate;

import com.prosports.models.Address;
import com.prosports.models.CustomerGroup;
import com.prosports.models.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDetailsRequest {
	@NotBlank(message = "Username is mandatory")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
	private String username;

	@NotBlank(message = "First name is mandatory")
	@Size(max = 50, message = "First name must be less than 50 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must only contain letters")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	@Size(max = 50, message = "Last name must be less than 50 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must only contain letters")
	private String lastName;

	@NotBlank(message = "Phone number is mandatory")
	@Pattern(regexp = "^[1-9]\\d{9}$", message = "Phone number must be exactly 10 digits and cannot start with 0")
	private String phoneNumber;

	@NotNull(message = "Gender is mandatory")
	private Gender gender;

	@NotNull(message = "Address is mandatory")
	private Address address;

	private LocalDate dob;
	
	@NotNull(message = "Customer Group Must be PRIME or NON_PRIME")
	private CustomerGroup customerGroup;
}
