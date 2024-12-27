package com.prosports.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dtos.CustomerCountryResponse;
import com.prosports.dtos.CustomerDetailsResponse;
import com.prosports.dtos.CustomerUpdateRequest;
import com.prosports.exceptions.UserNotFoundException;
import com.prosports.models.Customer;
import com.prosports.models.User;
import com.prosports.repositories.CustomerRepository;
import com.prosports.repositories.UsersRepository;
import com.prosports.services.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	// Autowiring repositories and model mapper to perform necessary operations
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Fetches customer details by their username. This method retrieves the user by
	 * username, then fetches the associated customer details. If the user does not
	 * exist, it throws a UserNotFoundException.
	 * 
	 * @param username The username of the customer
	 * @return CustomerDetailsResponse containing the customer details
	 * @throws UserNotFoundException If the user is not found with the provided
	 *                               username
	 */
	@Override
	public CustomerDetailsResponse getCustomerDetailsByUsername(String username) {
		// Find the user by the given username
		User user = usersRepository.findByUsername(username);

		// If the user exists, retrieve the associated customer details
		if (user != null) {
			// Fetch customer details from the repository by user ID
			Customer customer = customerRepository.findByUserUserId(user.getUserId())
					.orElseThrow(() -> new UserNotFoundException("User not found with User Name: " + username));

			// Map the customer entity to the response DTO and return it
			return modelMapper.map(customer, CustomerDetailsResponse.class);
		} else {
			// If user is not found, throw a UserNotFoundException
			throw new UserNotFoundException("User not found with User Name: " + username);
		}
	}

	/**
	 * Updates customer details based on the provided request object. This method
	 * fetches the user by username, finds the associated customer, and updates the
	 * details. After updating, it returns the updated customer details.
	 * 
	 * @param request The request containing updated customer information
	 * @return CustomerDetailsResponse with updated customer details
	 * @throws UserNotFoundException If the user is not found with the provided
	 *                               username
	 */
	@Transactional
	@Override
	public CustomerDetailsResponse updateCustomerByUsername(CustomerUpdateRequest request) {
		// Find the user by the given username in the update request
		User user = usersRepository.findByUsername(request.getUsername());

		// If the user exists, retrieve and update the associated customer details
		if (user != null) {
			// Fetch the existing customer from the repository using the user ID
			Customer customer = customerRepository.findByUserUserId(user.getUserId()).orElseThrow(
					() -> new UserNotFoundException("User not found with User Name: " + request.getUsername()));
			modelMapper.map(request, customer);
			customer = customerRepository.save(customer);

			// Map the updated customer entity to the response DTO and return it
			return modelMapper.map(customer, CustomerDetailsResponse.class);
		} else {
			// If user is not found, throw a UserNotFoundException
			throw new UserNotFoundException("User not found with User Name: " + request.getUsername());
		}
	}

	/**
	 * Fetches the customer's country and customer group based on their username.
	 * The method retrieves the user, fetches the associated customer details, and
	 * returns a response containing the customer's country and customer group.
	 * 
	 * @param username The username of the customer
	 * @return CustomerCountryResponse containing the customer's country and group
	 * @throws UserNotFoundException If the user is not found with the provided
	 *                               username
	 */
	@Override
	public CustomerCountryResponse getCustomerCountry(String username) {
		// Find the user by the given username
		User user = usersRepository.findByUsername(username);

		// If the user exists, retrieve the associated customer and return country
		// details
		if (user != null) {
			// Fetch customer details from the repository by user ID
			Customer customer = customerRepository.findByUserUserId(user.getUserId())
					.orElseThrow(() -> new UserNotFoundException("User not found with User Name: " + username));

			// Create a response object to hold country and customer group details
			CustomerCountryResponse customerCountryResponse = new CustomerCountryResponse();
			customerCountryResponse.setCountry(customer.getAddress().getCountry()); // Set the customer's country
			customerCountryResponse.setCustomerGroup(customer.getCustomerGroup().toString()); // Set the customer group

			// Return the populated response
			return customerCountryResponse;
		} else {
			// If user is not found, throw a UserNotFoundException
			throw new UserNotFoundException("User not found with User Name: " + username);
		}
	}
}
