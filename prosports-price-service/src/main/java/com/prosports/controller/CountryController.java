package com.prosports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dto.CountryRequestDto;
import com.prosports.dto.CountryResponseDto;
import com.prosports.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	private CountryService countryService;

	// Endpoint to add a new country and it's respective currency 
	@PostMapping("/add")
	public ResponseEntity<CountryResponseDto> addCountry(@RequestBody CountryRequestDto countryRequestDto) {
		return new ResponseEntity<CountryResponseDto>(countryService.addCountry(countryRequestDto), HttpStatus.CREATED);
	}
}
