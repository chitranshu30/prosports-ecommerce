package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.models.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	ProductType findProductTypeByProductTypeName(String name);

	boolean existsByProductTypeName(String productTypeName);

}
