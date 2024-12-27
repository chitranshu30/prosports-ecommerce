package com.prosports.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CustomerId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	private String firstName;
	private String lastName;
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Embedded
	private Address address;
	private LocalDate dob;
	
	@Enumerated(EnumType.STRING)
	private CustomerGroup customerGroup;
}
