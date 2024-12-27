package com.prosports.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prosports.dtos.MultiPackRequestDto;
import com.prosports.dtos.MultiPackResponseDto;
import com.prosports.service.MultiPackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/multi-pack")
public class MultiPackController {

	@Autowired
	private MultiPackService multiPackService;

	@PostMapping("/create")
	public ResponseEntity<MultiPackResponseDto> createMultiPack(
			@Valid @RequestBody MultiPackRequestDto multiPackRequestDto) {
		return new ResponseEntity<MultiPackResponseDto>(multiPackService.createMultiPack(multiPackRequestDto),
				HttpStatus.CREATED);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<MultiPackResponseDto>> findAllMultiPack() {
		return new ResponseEntity<List<MultiPackResponseDto>>(multiPackService.findAllMultiPack(), HttpStatus.OK);
	}

}
