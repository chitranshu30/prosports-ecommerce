package com.prosports.dtos;

import java.time.LocalDate;

import com.prosports.models.Address;
import com.prosports.models.CustomerGroup;
import com.prosports.models.Gender;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerUpdateRequest {

	// Username: Required, 3-20 characters, only alphanumeric and underscores
	@NotNull(message = "Username cannot be null")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
	private String username;

	// First Name: Optional, max length 50, only letters
	@Size(max = 50, message = "First name must be less than 50 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must only contain letters")
	private String firstName;

	// Last Name: Optional, max length 50, only letters
	@Size(max = 50, message = "Last name must be less than 50 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must only contain letters")
	private String lastName;

	// Phone Number: Required, must be 10 digits and not start with 0
	@NotNull(message = "Phone number cannot be null")
	@Pattern(regexp = "^[1-9]\\d{9}$", message = "Phone number must be exactly 10 digits and cannot start with 0")
	private String phoneNumber;

	// Gender: Optional
	private Gender gender;

	// Address: Optional
	private Address address;

	// Date of Birth: Required, must be in the past
	@NotNull(message = "Date of birth cannot be null")
	@Past(message = "Date of birth must be in the past")
	private LocalDate dob;

	// Customer Group: Optional
	private CustomerGroup customerGroup;
}
