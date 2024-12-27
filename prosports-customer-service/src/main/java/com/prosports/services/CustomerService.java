package com.prosports.services;

import com.prosports.dtos.CustomerCountryResponse;
import com.prosports.dtos.CustomerDetailsRequest;
import com.prosports.dtos.CustomerDetailsResponse;
import com.prosports.dtos.CustomerUpdateRequest;

public interface CustomerService {
	public CustomerDetailsResponse getCustomerDetailsByUsername(String username);

	public CustomerDetailsResponse updateCustomerByUsername(CustomerUpdateRequest request);
	
	public CustomerCountryResponse getCustomerCountry(String username);
}
