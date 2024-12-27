package com.prosports.dtos;

import com.prosports.models.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
	private String token;
	private String username;
	private String email;
	private Role role;
}
