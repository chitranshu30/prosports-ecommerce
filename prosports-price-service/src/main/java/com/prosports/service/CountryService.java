package com.prosports.service;

import com.prosports.dto.CountryRequestDto;
import com.prosports.dto.CountryResponseDto;

public interface CountryService {

	CountryResponseDto addCountry(CountryRequestDto countryRequestDto);

}