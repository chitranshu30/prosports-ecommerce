package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findProductByProductName(String name);

	boolean existsByProductName(String productName);

}
