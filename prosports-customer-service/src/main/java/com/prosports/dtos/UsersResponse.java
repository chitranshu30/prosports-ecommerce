package com.prosports.dtos;

import com.prosports.models.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersResponse {
	private String username;
	private String email;
	private Role role;
}
