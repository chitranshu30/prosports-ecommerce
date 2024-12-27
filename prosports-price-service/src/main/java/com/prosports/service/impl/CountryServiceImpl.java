package com.prosports.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dto.CountryRequestDto;
import com.prosports.dto.CountryResponseDto;
import com.prosports.entity.Country;
import com.prosports.exceptions.CountryAlreadyExistsException;
import com.prosports.repository.CountryRepository;
import com.prosports.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
     * Adds a new country to the repository. Converts the CountryRequestDto to a Country entity,
     * saves it to the repository, and then converts the saved entity back to a CountryResponseDto.
     * Throws CountryAlreadyExistsException if the country already exists.
     *
     * @param countryRequestDto the data transfer object containing the details of the country to be added
     * @return a CountryResponseDto containing the details of the saved country
     */
	@Override
	public CountryResponseDto addCountry(CountryRequestDto countryRequestDto) {
		if(!countryRepository.existsByCountryName(countryRequestDto.getCountryName())) {
			Country customerCurrency = modelMapper.map(countryRequestDto, Country.class);
			countryRepository.save(customerCurrency);
			return modelMapper.map(customerCurrency, CountryResponseDto.class);			
		} else
			throw new CountryAlreadyExistsException("Country with name " + countryRequestDto.getCountryName() + " already exists.");
	}

}
