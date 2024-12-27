package com.prosports.services;

import java.util.List;

import com.prosports.dtos.LoginResponse;
import com.prosports.dtos.UsersLoginRequest;
import com.prosports.dtos.UsersRegistrationRequest;
import com.prosports.dtos.UsersResponse;

public interface UserService {
	public UsersResponse registerUser(UsersRegistrationRequest request);

	public LoginResponse loginUser(UsersLoginRequest request);

	public List<UsersResponse> getAllUsers();

	void validateToken(String token);	
}
