package com.prosports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Country findCountryBycountryName(String countryName);

	boolean existsByCountryName(String countryName);
	
}
