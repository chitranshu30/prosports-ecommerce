package com.prosports.dtos;

import java.time.LocalDate;
import java.time.Period;

import com.prosports.models.Address;
import com.prosports.models.CustomerGroup;
import com.prosports.models.Gender;
import com.prosports.models.Role;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UsersRegistrationRequest {
	@NotBlank(message = "Username is mandatory")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
	private String username;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
	private String password;

	@NotNull
	private Role role;

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

	public int getAge() {
		if (dob == null) {
			return -1; // return a default value if dob is not set
		}
		Period period = Period.between(dob, LocalDate.now());
		return period.getYears();
	}

	// Validate age between 13 and 100
	public boolean isValidAge() {
		int age = getAge();
		return age >= 13 && age <= 100;
	}

	@AssertTrue(message = "Age must be greater than 13")
	public boolean isAgeValid() {
		return isValidAge();
	}

	@NotNull(message = "Customer group Should be PRIME or NON_PRIME and is mandatory")
	private CustomerGroup customerGroup;
}
