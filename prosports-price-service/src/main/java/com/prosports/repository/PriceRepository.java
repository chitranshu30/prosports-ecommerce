package com.prosports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.entity.Country;
import com.prosports.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	List<Price> findPriceByProductVariantsku(String sku);

	Price findByCountryAndProductVariantsku(Country country, String productVariantsku);
}
