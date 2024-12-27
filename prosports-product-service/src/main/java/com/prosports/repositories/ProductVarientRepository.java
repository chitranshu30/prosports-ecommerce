package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.models.ProductVariant;

@Repository
public interface ProductVarientRepository extends JpaRepository<ProductVariant, Long> {

	ProductVariant findProductVariantByProductVariantSku(String productVariantSku);

}
