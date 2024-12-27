package com.prosports.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dtos.CustomerCountryResponse;
import com.prosports.dtos.CustomerDetailsResponse;
import com.prosports.dtos.CustomerUpdateRequest;
import com.prosports.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService; // Injecting the CustomerService

	// Endpoint to get customer details by username
	@GetMapping("/details/{username}") 
	public ResponseEntity<CustomerDetailsResponse> getCustomerDetailsByUsername(@PathVariable String username) {
		return new ResponseEntity<CustomerDetailsResponse>(customerService.getCustomerDetailsByUsername(username),
				HttpStatus.OK);
	}

	 // Endpoint to update customer details
	@PutMapping("/update")
	public ResponseEntity<CustomerDetailsResponse> updateCustomerByUsername(
			@Valid @RequestBody CustomerUpdateRequest request) {
		return new ResponseEntity<CustomerDetailsResponse>(customerService.updateCustomerByUsername(request),
				HttpStatus.OK);
	}

	// Endpoint to get customer country information by username
	@GetMapping("/country/{username}") 
	public ResponseEntity<CustomerCountryResponse> getCustomerCountry(@PathVariable String username) {
		return new ResponseEntity<CustomerCountryResponse>(customerService.getCustomerCountry(username), HttpStatus.OK);
	}
}
