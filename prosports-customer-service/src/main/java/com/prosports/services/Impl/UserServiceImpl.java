package com.prosports.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prosports.config.JwtUtil;
import com.prosports.dtos.LoginResponse;
import com.prosports.dtos.UsersLoginRequest;
import com.prosports.dtos.UsersRegistrationRequest;
import com.prosports.dtos.UsersResponse;
import com.prosports.exceptions.InvalidUsernameOrPassword;
import com.prosports.exceptions.UserAlreadyExistsException;
import com.prosports.exceptions.UserNotFoundException;
import com.prosports.models.Admin;
import com.prosports.models.Customer;
import com.prosports.models.Role;
import com.prosports.models.User;
import com.prosports.repositories.AdminRepository;
import com.prosports.repositories.CustomerRepository;
import com.prosports.repositories.UsersRepository;
import com.prosports.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	// Inject dependencies for ModelMapper, repositories, and password encoder
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private JwtUtil jwtUtil;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * Registers a new user based on the information provided in the registration
	 * request. The method checks if the username already exists, encrypts the
	 * user's password, and saves the user either as a customer or an admin
	 * depending on the role.
	 * 
	 * @param request the user registration request containing the user details
	 * @return UsersResponse DTO containing the registered user information
	 * @throws UserAlreadyExistsException if the username already exists in the
	 *                                    system
	 */
	@Transactional
	@Override
	public UsersResponse registerUser(UsersRegistrationRequest request) {
		// Check if a user already exists with the same username
		if (!usersRepository.existsByUsername(request.getUsername())) {

			// Map UsersRegistrationRequest to User entity using ModelMapper
			User user = modelMapper.map(request, User.class);

			// Encode the user's password before saving
			user.setPassword(encodePassword(request.getPassword()));

			// If the user is a customer, create and save the customer entity
			if (Role.ROLE_CUSTOMER.equals(request.getRole())) {
				Customer customer = modelMapper.map(request, Customer.class);
				customer.setUser(user);
				customerRepository.save(customer);

				// If the user is an admin, create and save the admin entity
			} else if (Role.ROLE_ADMIN.equals(request.getRole())) {
				Admin admin = modelMapper.map(request, Admin.class);
				admin.setUser(user);
				adminRepository.save(admin);
			}

			// Map the created user entity to UsersResponse DTO and return it
			return modelMapper.map(user, UsersResponse.class);
		} else {
			// If the username already exists, throw a custom exception
			throw new UserAlreadyExistsException(
					"Username or Email already exists with username: " + request.getUsername());
		}
	}

	/**
	 * Logs in the user by validating the username and password. If the credentials
	 * are valid, a JWT token is generated and returned along with user details.
	 * 
	 * @param request the login request containing the username and password
	 * @return LoginResponse containing the generated JWT token and user details
	 * @throws InvalidUsernameOrPassword if the username or password is invalid
	 */
	@Override
	public LoginResponse loginUser(UsersLoginRequest request) {
		// Find the user by username
		User user = usersRepository.findByUsername(request.getUsername());

		// Check if the user exists
		if (user != null) {
			// Validate the password using BCryptPasswordEncoder
			if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

				// If the user is a customer, check if the customer exists
				if (Role.ROLE_CUSTOMER.equals(user.getRole())) {
					customerRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UserNotFoundException(
							"Customer not found with User Name: " + request.getUsername()));
				}

				// If the user is an admin, check if the admin exists
				else if (Role.ROLE_ADMIN.equals(user.getRole())) {
					adminRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UserNotFoundException(
							"Admin not found with User Name: " + request.getUsername()));
				}

				// Generate a JWT token for the user
				String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

				// Create and populate the LoginResponse DTO with token and user details
				LoginResponse loginResponse = new LoginResponse();
				loginResponse.setToken(token);
				loginResponse.setUsername(user.getUsername());
				loginResponse.setEmail(user.getEmail());
				loginResponse.setRole(user.getRole());

				return loginResponse;
			} else {
				// If the password does not match, throw an exception
				throw new InvalidUsernameOrPassword("Invalid username or password");
			}
		} else {
			// If the user is not found, throw an exception
			throw new InvalidUsernameOrPassword("Invalid username or password");
		}
	}

	/**
	 * Fetches all users in the system and maps them to a list of UsersResponse
	 * DTOs.
	 * 
	 * @return a list of UsersResponse DTOs containing user information
	 */
	@Override
	public List<UsersResponse> getAllUsers() {
		// Fetch all users from the repository and map them to UsersResponse DTOs
		return usersRepository.findAll().stream().map((element) -> modelMapper.map(element, UsersResponse.class))
				.collect(Collectors.toList());
	}

	/**
	 * Validates the provided JWT token to ensure its authenticity.
	 * 
	 * @param token the JWT token to validate
	 */
	@Override
	public void validateToken(String token) {
		// Call the JwtUtil service to validate the token
		jwtUtil.validateToken(token);
	}

	/**
	 * Encodes a plain text password using BCryptPasswordEncoder.
	 * 
	 * @param password the plain text password to encode
	 * @return the encoded password
	 */
	private String encodePassword(String password) {
		// Encode the password using BCryptPasswordEncoder
		return new BCryptPasswordEncoder().encode(password);
	}
}
