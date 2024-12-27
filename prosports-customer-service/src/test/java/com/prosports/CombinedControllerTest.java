package com.prosports;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.prosports.controllers.UsersController;
import com.prosports.dtos.LoginResponse;
import com.prosports.dtos.UsersLoginRequest;
import com.prosports.dtos.UsersRegistrationRequest;
import com.prosports.dtos.UsersResponse;
import com.prosports.models.CustomerGroup;
import com.prosports.models.Role;
import com.prosports.services.UserService;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UsersController usersController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
	}

	@Test
	void registerUser_Success() throws Exception {
		// Arrange
		UsersRegistrationRequest registrationRequest = new UsersRegistrationRequest();
		registrationRequest.setUsername("john_doe");
		registrationRequest.setPassword("password");
		registrationRequest.setRole(Role.ROLE_CUSTOMER);

		UsersResponse usersResponse = new UsersResponse();
		usersResponse.setUsername("john_doe");
		usersResponse.setRole(Role.ROLE_CUSTOMER);

		when(userService.registerUser(any(UsersRegistrationRequest.class))).thenReturn(usersResponse);

		// Act & Assert
		mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"john_doe\", \"password\": \"password\", \"role\": \"ROLE_CUSTOMER\" }"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.username").value("john_doe"))
				.andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"));
	}

	@Test
	void loginUser_Success() throws Exception {
		// Arrange
		UsersLoginRequest loginRequest = new UsersLoginRequest();
		loginRequest.setUsername("john_doe");
		loginRequest.setPassword("password");

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUsername("john_doe");
		loginResponse.setToken("some_jwt_token");

		when(userService.loginUser(any(UsersLoginRequest.class))).thenReturn(loginResponse);

		// Act & Assert
		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"john_doe\", \"password\": \"password\" }")).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("john_doe"))
				.andExpect(jsonPath("$.token").value("some_jwt_token"));
	}

	@Test
	void getAllUsers_Success() throws Exception {
		// Arrange
		UsersResponse userResponse = new UsersResponse();
		userResponse.setUsername("john_doe");
		userResponse.setRole(Role.ROLE_CUSTOMER);

		when(userService.getAllUsers()).thenReturn(List.of(userResponse));

		// Act & Assert
		mockMvc.perform(get("/users/all")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].username").value("john_doe"))
				.andExpect(jsonPath("$[0].role").value("ROLE_CUSTOMER"));
	}

	@Test
	void validateToken_Success() throws Exception {
		// Arrange
		String token = "some_jwt_token";
		doNothing().when(userService).validateToken(anyString());

		// Act & Assert
		mockMvc.perform(get("/users/validate-token").param("token", token)).andExpect(status().isOk())
				.andExpect(content().string("Token validated successfully."));
	}

	@Test
	void validateToken_Failure() throws Exception {
		// Arrange
		String token = "invalid_token";
		doThrow(new RuntimeException("Token validation failed")).when(userService).validateToken(anyString());

		// Act & Assert
		mockMvc.perform(get("/users/validate-token").param("token", token)).andExpect(status().isUnauthorized())
				.andExpect(content().string("Token validation failed: Token validation failed"));
	}
}
