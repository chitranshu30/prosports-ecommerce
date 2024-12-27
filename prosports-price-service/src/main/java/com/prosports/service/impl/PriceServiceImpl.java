package com.prosports.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dto.OnlyPriceResponseDto;
import com.prosports.dto.PriceRequestDto;
import com.prosports.dto.PriceResponseDto;
import com.prosports.entity.Country;
import com.prosports.entity.Price;
import com.prosports.exceptions.PriceAlreadyExistsException;
import com.prosports.exceptions.ResourceNotFoundException;
import com.prosports.repository.CountryRepository;
import com.prosports.repository.PriceRepository;
import com.prosports.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceRepository priceRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Adds a new price to the repository. Converts the PriceRequestDto to a Price
	 * entity, applies customer group logic, and saves it to the repository. Throws
	 * PriceAlreadyExistsException if the price for the given country and product
	 * variant SKU already exists. Throws ResourceNotFoundException if the country
	 * is not found.
	 *
	 * @param priceRequestDto the data transfer object containing the details of the
	 *                        price to be added
	 * @return a PriceResponseDto containing the details of the saved price
	 */
	@Override
	public PriceResponseDto addPrice(PriceRequestDto priceRequestDto) {
		String countryName = priceRequestDto.getCountry().getCountryName();
		Country country = countryRepository.findCountryBycountryName(countryName);
		if (country != null) {
			Price price = modelMapper.map(priceRequestDto, Price.class);
			String customerGroup;
			BigDecimal newPrice = price.getAmount();
			if (Math.random() > 0.5) {
				customerGroup = "PRIME";
				newPrice = newPrice.subtract(price.getAmount().multiply(BigDecimal.valueOf(0.12)));
				price.setAmount(newPrice);
			} else {
				customerGroup = "NON-PRIME";
			}
			price.setCustomerGroup(customerGroup);
			price.setCountry(country);
			Price existingPrice = priceRepository.findByCountryAndProductVariantsku(price.getCountry(),
					price.getProductVariantsku());
			if (existingPrice == null) {
				priceRepository.save(price);
				return modelMapper.map(price, PriceResponseDto.class);
			} else
				throw new PriceAlreadyExistsException(
						"Price for the given country and product variant SKU combination already exists.");
		} else
			throw new ResourceNotFoundException("Country with name " + countryName + " not found.");
	}

	/**
	 * Finds the price by product variant SKU. Filters the price list by country
	 * name which is provided by the customer-service for the particular logged-in
	 * user and returns the first matching price. If no matching price is found,
	 * returns null.
	 *
	 * @param sku the product variant SKU
	 * @return an OnlyPriceResponseDto containing the price details
	 */
	@Override
	public OnlyPriceResponseDto findPriceBySku(String sku) {
		List<Price> priceList = priceRepository.findPriceByProductVariantsku(sku);
		if (!priceList.isEmpty()) {
//		Logic to call customer to get the conuntry
			String countryName = "India";
			Price price = priceList.stream().filter(prices -> prices.getCountry().getCountryName().equals(countryName))
					.findFirst().orElse(null);
			return modelMapper.map(price, OnlyPriceResponseDto.class);
		} else {
			throw new ResourceNotFoundException("No Price found for the sku " + sku);
		}
	}
}
