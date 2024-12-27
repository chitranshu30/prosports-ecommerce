package com.prosports.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dtos.LoginResponse;
import com.prosports.dtos.UsersLoginRequest;
import com.prosports.dtos.UsersRegistrationRequest;
import com.prosports.dtos.UsersResponse;
import com.prosports.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService userService; // Injecting the UserService to handle user-related business logic

	// Endpoint for registering a new user
	@PostMapping("/register")
	public ResponseEntity<UsersResponse> registerUser(@Valid @RequestBody UsersRegistrationRequest request) {
		return new ResponseEntity<UsersResponse>(userService.registerUser(request), HttpStatus.CREATED);
	}

	// Endpoint for user login
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody UsersLoginRequest request) {
		return new ResponseEntity<LoginResponse>(userService.loginUser(request), HttpStatus.OK);
	}

	// Endpoint to get the list of all users
	@GetMapping("/all")
	public ResponseEntity<List<UsersResponse>> getAllUsers() {
		return new ResponseEntity<List<UsersResponse>>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	// Validate Token Method
	@GetMapping("/validate-token")
	public ResponseEntity<?> validateToken(@RequestParam String token) {
		System.out.println("Validating the Token");
		try {
			userService.validateToken(token); // Call the service to validate the token
			return ResponseEntity.ok("Token validated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed: " + e.getMessage());
		}
	}
}
