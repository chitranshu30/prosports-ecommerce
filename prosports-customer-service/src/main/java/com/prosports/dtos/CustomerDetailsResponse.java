package com.prosports.dtos;

import java.time.LocalDate;

import com.prosports.models.Address;
import com.prosports.models.CustomerGroup;
import com.prosports.models.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDetailsResponse {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Gender gender;
	private Address address;
	private LocalDate dob;
	private CustomerGroup customerGroup;
}
